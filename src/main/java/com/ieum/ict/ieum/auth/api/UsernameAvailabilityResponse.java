package com.ieum.ict.ieum.auth.api;

import io.swagger.v3.oas.annotations.media.Schema;

public record UsernameAvailabilityResponse(
        @Schema(description = "사용 가능 여부", example = "true")
        boolean available
) {
}
