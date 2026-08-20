package com.ieum.ict.ieum.auth.api;

import com.ieum.ict.ieum.auth.service.AuthService;
import com.ieum.ict.ieum.common.api.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Void> signup(@Valid @RequestBody AuthRequest.Signup request) {
        authService.signup(request);
        return CommonResponse.ok(null);
    }

    @PostMapping("/login")
    public CommonResponse<AuthResponse> login(@Valid @RequestBody AuthRequest.Login request) {
        return CommonResponse.ok(authService.login(request));
    }

    @PostMapping("/refresh")
    public CommonResponse<AuthResponse> refresh(@Valid @RequestBody AuthRequest.Refresh request) {
        return CommonResponse.ok(authService.refresh(request));
    }

    @PostMapping("/logout")
    public CommonResponse<Void> logout(@Valid @RequestBody AuthRequest.Logout request) {
        authService.logout(request);
        return CommonResponse.ok(null);
    }
}
