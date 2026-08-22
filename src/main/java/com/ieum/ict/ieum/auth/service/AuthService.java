package com.ieum.ict.ieum.auth.service;

import com.ieum.ict.ieum.auth.api.AuthRequest;
import com.ieum.ict.ieum.auth.api.AuthResponse;
import com.ieum.ict.ieum.auth.domain.User;
import com.ieum.ict.ieum.auth.domain.UserRole;
import com.ieum.ict.ieum.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final com.ieum.ict.ieum.auth.security.JwtTokenProvider jwtTokenProvider;

    public void signup(AuthRequest.Signup request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 가입된 이메일입니다.");
        }
        userRepository.save(new User(request.email(), passwordEncoder.encode(request.password()), request.name()));
    }

    public AuthResponse login(AuthRequest.Login request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다."));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        String accessToken = jwtTokenProvider.create(user.getEmail(), "access", user.getRole().name(), 3600000);
        String refreshToken = jwtTokenProvider.create(user.getEmail(), "refresh", user.getRole().name(), 1209600000);
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);
        return new AuthResponse(accessToken, refreshToken);
    }

    public AuthResponse refresh(AuthRequest.Refresh request) {
        if (!jwtTokenProvider.isValid(request.refreshToken())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 Refresh Token입니다.");
        }
        var claims = jwtTokenProvider.parse(request.refreshToken());
        if (!"refresh".equals(claims.get("type", String.class))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token이 아닙니다.");
        }
        User user = userRepository.findByEmail(claims.getSubject())
                .filter(candidate -> request.refreshToken().equals(candidate.getRefreshToken()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh Token이 일치하지 않습니다."));
        String accessToken = jwtTokenProvider.create(user.getEmail(), "access", user.getRole().name(), 3600000);
        return new AuthResponse(accessToken, request.refreshToken());
    }

    public AuthResponse adminLogin(AuthRequest.Login request) {
        User user = userRepository.findByEmail(request.email())
                .filter(candidate -> candidate.getRole() == UserRole.ADMIN)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자 계정 정보가 올바르지 않습니다."));
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자 계정 정보가 올바르지 않습니다.");
        }
        String accessToken = jwtTokenProvider.create(user.getEmail(), "access", "ADMIN", 3600000);
        String refreshToken = jwtTokenProvider.create(user.getEmail(), "refresh", "ADMIN", 1209600000);
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);
        return new AuthResponse(accessToken, refreshToken);
    }

    public void logout(AuthRequest.Logout request) {
        if (!jwtTokenProvider.isValid(request.refreshToken())) {
            return;
        }
        userRepository.findByEmail(jwtTokenProvider.parse(request.refreshToken()).getSubject())
                .ifPresent(user -> { if (request.refreshToken().equals(user.getRefreshToken())) {
                    user.clearRefreshToken(); userRepository.save(user);
                }});
    }
}
