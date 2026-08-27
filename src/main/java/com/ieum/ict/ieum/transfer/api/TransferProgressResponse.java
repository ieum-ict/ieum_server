package com.ieum.ict.ieum.transfer.api;

import com.ieum.ict.ieum.transfer.domain.TransferStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public record TransferProgressResponse(
        @Schema(description = "현재 이송 상태", example = "REQUESTED")
        TransferStatus currentStatus,
        @Schema(description = "병원 응답 대기 중인 수용 요청 수", example = "14")
        long pendingResponseCount,
        @Schema(description = "이송 상태 변경 이력")
        List<TransferHistoryResponse> history
) {
}
