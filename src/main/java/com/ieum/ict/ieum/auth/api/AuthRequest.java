package com.ieum.ict.ieum.auth.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class AuthRequest {
    private AuthRequest() {}
    public record Signup(@NotBlank @Email String email,
                         @NotBlank @Size(min = 4, max = 30) String loginId,
                         @NotBlank @Size(min = 8) String password,
                         @NotBlank String name) {}
    public record Login(@NotBlank String loginId, @NotBlank String password) {}
    public record Refresh(@NotBlank String refreshToken) {}
    public record Logout(@NotBlank String refreshToken) {}
}
