package com.ieum.ict.ieum.transfer.api;

import com.ieum.ict.ieum.transfer.domain.TransferStatus;
import com.ieum.ict.ieum.transfer.domain.TransferStatusHistory;
import java.time.LocalDateTime;

public record TransferHistoryResponse(TransferStatus status, LocalDateTime changedAt) {
    public static TransferHistoryResponse from(TransferStatusHistory history) {
        return new TransferHistoryResponse(history.getStatus(), history.getChangedAt());
    }
}
