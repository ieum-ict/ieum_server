package com.ieum.ict.ieum.admin.api;

import com.ieum.ict.ieum.auth.domain.User;
import com.ieum.ict.ieum.auth.domain.UserRole;

public record AdminUserResponse(Long id, String email, String name, UserRole role) {
    public static AdminUserResponse from(User user) { return new AdminUserResponse(user.getId(), user.getEmail(), user.getName(), user.getRole()); }
}
