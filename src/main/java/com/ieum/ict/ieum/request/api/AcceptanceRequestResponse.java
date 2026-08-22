package com.ieum.ict.ieum.request.api;

import com.ieum.ict.ieum.request.domain.AcceptanceRequest;
import com.ieum.ict.ieum.request.domain.AcceptanceRequestStatus;
import java.time.LocalDateTime;

public record AcceptanceRequestResponse(Long id, Long transferId, String hospitalId,
                                        String content, AcceptanceRequestStatus status,
                                        LocalDateTime createdAt, LocalDateTime respondedAt) {
    public static AcceptanceRequestResponse from(AcceptanceRequest request) {
        return new AcceptanceRequestResponse(request.getId(), request.getTransfer().getId(), request.getHospitalId(),
                request.getContent(), request.getStatus(), request.getCreatedAt(), request.getRespondedAt());
    }
}
