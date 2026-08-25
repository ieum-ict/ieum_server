package com.ieum.ict.ieum.auth.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class AuthRequest {
    private AuthRequest() {}
    public record Signup(@NotBlank @Email String email,
                         @Schema(description = "회원가입 시 사용할 로그인 아이디")
                         @NotBlank @Size(min = 4, max = 30) String username,
                         @NotBlank @Size(min = 8) String password,
                         @NotBlank String name) {}
    public record Login(@Schema(description = "회원가입 시 등록한 로그인 아이디")
                        @NotBlank String username,
                        @NotBlank String password) {}
    public record Refresh(@NotBlank String refreshToken) {}
    public record Logout(@NotBlank String refreshToken) {}
}
