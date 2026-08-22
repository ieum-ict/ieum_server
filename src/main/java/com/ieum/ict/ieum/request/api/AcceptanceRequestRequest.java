package com.ieum.ict.ieum.request.api;

import com.ieum.ict.ieum.request.domain.AcceptanceRequestStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public final class AcceptanceRequestRequest {
    private AcceptanceRequestRequest() {}
    public record Create(@NotNull Long transferId, @NotBlank String hospitalId,
                         @NotBlank @Size(max = 2000) String content) {}
    public record BulkCreate(@NotNull Long transferId, @NotEmpty List<@NotBlank String> hospitalIds,
                             @NotBlank @Size(max = 2000) String content) {}
    public record Response(@NotNull AcceptanceRequestStatus status,
                           @NotBlank @Size(max = 2000) String content) {}
}
