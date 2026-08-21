package com.ieum.ict.ieum.transfer.domain;

import com.ieum.ict.ieum.auth.domain.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transfer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;
    @Column(nullable = false)
    private String patientName;
    @Column(nullable = false)
    private Integer patientAge;
    @Column(nullable = false, length = 1000)
    private String symptom;
    @Column(nullable = false)
    private String departureAddress;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransferStatus status;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Transfer(User requester, String patientName, Integer patientAge, String symptom, String departureAddress) {
        this.requester = requester;
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.symptom = symptom;
        this.departureAddress = departureAddress;
        this.status = TransferStatus.REQUESTED;
        this.createdAt = LocalDateTime.now();
    }
}
