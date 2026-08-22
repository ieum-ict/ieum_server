package com.ieum.ict.ieum.hospital.api;

import jakarta.validation.constraints.NotBlank;

public final class HospitalRequest {
    private HospitalRequest() {}
    public record Search(@NotBlank String keyword) {}
    public record ResourceUpdate(@NotBlank String content) {}
}
