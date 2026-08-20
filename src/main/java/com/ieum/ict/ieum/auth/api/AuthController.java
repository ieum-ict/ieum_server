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
}
