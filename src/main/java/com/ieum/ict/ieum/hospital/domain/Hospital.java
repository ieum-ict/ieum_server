package com.ieum.ict.ieum.hospital.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hospital {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false, length = 5000)
    private String resourcesContent;
    @Column(nullable = false)
    private LocalDateTime resourcesUpdatedAt;

    public Hospital(String name, String address, String phone, String resourcesContent) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.resourcesContent = resourcesContent;
        this.resourcesUpdatedAt = LocalDateTime.now();
    }

    public void updateResources(String resourcesContent) {
        this.resourcesContent = resourcesContent;
        this.resourcesUpdatedAt = LocalDateTime.now();
    }

    public void update(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }
}
