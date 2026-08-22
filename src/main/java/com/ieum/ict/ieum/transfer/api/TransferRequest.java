package com.ieum.ict.ieum.transfer.api;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public final class TransferRequest {
    private TransferRequest() {}
    public record Create(@NotBlank String patientName, @NotNull @Min(0) Integer patientAge,
                         @NotBlank @Size(max = 1000) String symptom,
                         @NotBlank String departureAddress) {}
    public record Update(@NotBlank String patientName, @NotNull @Min(0) Integer patientAge,
                         @NotBlank @Size(max = 1000) String symptom,
                         @NotBlank String departureAddress) {}
    public record StatusUpdate(@NotNull com.ieum.ict.ieum.transfer.domain.TransferStatus status) {}
}
