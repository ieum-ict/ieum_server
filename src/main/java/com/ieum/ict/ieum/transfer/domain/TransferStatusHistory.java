package com.ieum.ict.ieum.transfer.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferStatusHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transfer_id", nullable = false)
    private Transfer transfer;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferStatus status;
    @Column(nullable = false)
    private LocalDateTime changedAt;

    public TransferStatusHistory(Transfer transfer, TransferStatus status) {
        this.transfer = transfer;
        this.status = status;
        this.changedAt = LocalDateTime.now();
    }
}
