package com.ieum.ict.ieum.auth.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class AuthRequest {
    private AuthRequest() {}
    public record Signup(@NotBlank @Email String email, @NotBlank @Size(min = 8) String password,
                         @NotBlank String name) {}
    public record Login(@NotBlank @Email String email, @NotBlank String password) {}
}
