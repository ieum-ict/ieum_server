package com.ieum.ict.ieum.admin.api;

import com.ieum.ict.ieum.auth.domain.UserRole;
import com.ieum.ict.ieum.request.domain.AcceptanceRequestStatus;
import com.ieum.ict.ieum.transfer.domain.TransferStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public final class AdminRequest {
    private AdminRequest() {}
    public record UserUpdate(@NotNull UserRole role) {}
    public record TransferStatusUpdate(@NotNull TransferStatus status) {}
    public record AcceptanceResponse(@NotNull AcceptanceRequestStatus status, @NotBlank String content) {}
    public record HospitalUpdate(@NotBlank String name, @NotBlank String address, @NotBlank String phone) {}
}
