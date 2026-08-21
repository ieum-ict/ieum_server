package com.ieum.ict.ieum.transfer.api;

import com.ieum.ict.ieum.transfer.domain.Transfer;
import com.ieum.ict.ieum.transfer.domain.TransferStatus;
import java.time.LocalDateTime;

public record TransferResponse(Long id, String patientName, Integer patientAge, String symptom,
                               String departureAddress, TransferStatus status, LocalDateTime createdAt) {
    public static TransferResponse from(Transfer transfer) {
        return new TransferResponse(transfer.getId(), transfer.getPatientName(), transfer.getPatientAge(),
                transfer.getSymptom(), transfer.getDepartureAddress(), transfer.getStatus(), transfer.getCreatedAt());
    }
}
