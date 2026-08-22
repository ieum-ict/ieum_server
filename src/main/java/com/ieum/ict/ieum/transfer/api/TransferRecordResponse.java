package com.ieum.ict.ieum.transfer.api;

import com.ieum.ict.ieum.transfer.domain.TransferRecord;
import java.time.LocalDateTime;

public record TransferRecordResponse(Long id, String type, String content, LocalDateTime createdAt) {
    public static TransferRecordResponse from(TransferRecord record) {
        return new TransferRecordResponse(record.getId(), record.getType(), record.getContent(), record.getCreatedAt());
    }
}
