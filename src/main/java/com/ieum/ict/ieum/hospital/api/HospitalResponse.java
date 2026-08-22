package com.ieum.ict.ieum.hospital.api;

import com.ieum.ict.ieum.hospital.domain.Hospital;
import java.time.LocalDateTime;

public record HospitalResponse(Long id, String name, String address, String phone,
                               String resourcesContent, LocalDateTime resourcesUpdatedAt) {
    public static HospitalResponse from(Hospital hospital) {
        return new HospitalResponse(hospital.getId(), hospital.getName(), hospital.getAddress(), hospital.getPhone(),
                hospital.getResourcesContent(), hospital.getResourcesUpdatedAt());
    }
}
