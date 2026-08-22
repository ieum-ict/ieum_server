package com.ieum.ict.ieum.hospital.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HospitalResourceHistory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hospital_id", nullable = false)
    private Hospital hospital;
    @Column(nullable = false, length = 5000)
    private String content;
    @Column(nullable = false)
    private LocalDateTime changedAt;

    public HospitalResourceHistory(Hospital hospital, String content) {
        this.hospital = hospital;
        this.content = content;
        this.changedAt = LocalDateTime.now();
    }
}
