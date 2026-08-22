package com.ieum.ict.ieum.request.domain;

import com.ieum.ict.ieum.auth.domain.User;
import com.ieum.ict.ieum.transfer.domain.Transfer;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AcceptanceRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transfer_id", nullable = false)
    private Transfer transfer;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @Column(nullable = false)
    private String hospitalId;
    @Column(nullable = false, length = 2000)
    private String content;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AcceptanceRequestStatus status;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime respondedAt;

    public AcceptanceRequest(Transfer transfer, User requester, String hospitalId, String content) {
        this.transfer = transfer;
        this.requester = requester;
        this.hospitalId = hospitalId;
        this.content = content;
        this.status = AcceptanceRequestStatus.REQUESTED;
        this.createdAt = LocalDateTime.now();
    }

    public void respond(AcceptanceRequestStatus status, String content) {
        this.status = status;
        this.content = content;
        this.respondedAt = LocalDateTime.now();
    }

    public void retry() {
        this.status = AcceptanceRequestStatus.REQUESTED;
        this.respondedAt = null;
    }
}
