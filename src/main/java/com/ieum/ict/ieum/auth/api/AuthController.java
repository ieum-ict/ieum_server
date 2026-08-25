package com.ieum.ict.ieum.auth.api;

import com.ieum.ict.ieum.auth.service.AuthService;
import com.ieum.ict.ieum.common.api.CommonResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponse<Void> signup(@Valid @RequestBody AuthRequest.Signup request) {
        authService.signup(request);
        return CommonResponse.ok(null);
    }

    @GetMapping("/username-availability")
    public CommonResponse<UsernameAvailabilityResponse> checkUsernameAvailability(
            @RequestParam @NotBlank @Size(min = 4, max = 30) String username) {
        return CommonResponse.ok(new UsernameAvailabilityResponse(authService.isUsernameAvailable(username)));
    }

    @PostMapping("/login")
    public CommonResponse<AuthResponse> login(@Valid @RequestBody AuthRequest.Login request) {
        return CommonResponse.ok(authService.login(request));
    }

    @PostMapping("/admin/login")
    public CommonResponse<AuthResponse> adminLogin(@Valid @RequestBody AuthRequest.Login request) {
        return CommonResponse.ok(authService.adminLogin(request));
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
