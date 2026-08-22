package com.ieum.ict.ieum.transfer.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransferRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "transfer_id", nullable = false)
    private Transfer transfer;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false, length = 5000)
    private String content;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public TransferRecord(Transfer transfer, String type, String content) {
        this.transfer = transfer;
        this.type = type;
        this.content = content;
        this.createdAt = LocalDateTime.now();
    }
}
