package com.ieum.ict.ieum.auth.service;

import com.ieum.ict.ieum.auth.api.AuthRequest;
import com.ieum.ict.ieum.auth.api.AuthResponse;
import com.ieum.ict.ieum.auth.domain.User;
import com.ieum.ict.ieum.auth.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
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
    @Value("${jwt.secret:ieum-local-secret-key-must-be-at-least-32-bytes}") private String secret;

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
        var now = new Date();
        String token = Jwts.builder().subject(String.valueOf(user.getId())).claim("email", user.getEmail())
                .issuedAt(now).expiration(new Date(now.getTime() + 3600000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8))).compact();
        return new AuthResponse(token);
    }
}
