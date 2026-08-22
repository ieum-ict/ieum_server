package com.ieum.ict.ieum.hospital.api;

import com.ieum.ict.ieum.hospital.domain.HospitalResourceHistory;
import java.time.LocalDateTime;

public record HospitalResourceHistoryResponse(String content, LocalDateTime changedAt) {
    public static HospitalResourceHistoryResponse from(HospitalResourceHistory history) {
        return new HospitalResourceHistoryResponse(history.getContent(), history.getChangedAt());
    }
}
