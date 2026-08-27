package com.ieum.ict.ieum.transfer.api;

import com.ieum.ict.ieum.transfer.domain.TransferStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

public record TransferProgressResponse(
        @Schema(description = "현재 이송 상태", example = "REQUESTED")
        TransferStatus currentStatus,
        @Schema(description = "화면에 표시할 현재 진행 단계", example = "HOSPITAL_RESPONSE_WAITING")
        TransferProgressStage currentStage,
        @Schema(description = "병원 응답 대기 중인 수용 요청 수", example = "14")
        long pendingResponseCount,
        @Schema(description = "화면에 표시할 전체 진행 단계")
        List<Stage> stages
) {
    public record Stage(
            @Schema(description = "진행 단계", example = "ACCEPTANCE_REQUEST_SENT")
            TransferProgressStage stage,
            @Schema(description = "단계 완료 시각. 아직 완료되지 않았으면 null")
            LocalDateTime completedAt,
            @Schema(description = "현재 진행 중인 단계 여부", example = "true")
            boolean current
    ) {
    }
}
