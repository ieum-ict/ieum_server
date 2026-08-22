package com.ieum.ict.ieum.admin.api;

import com.ieum.ict.ieum.auth.domain.UserRole;
import jakarta.validation.constraints.NotNull;

public final class AdminRequest {
    private AdminRequest() {}
    public record UserUpdate(@NotNull UserRole role) {}
}
