package com.ieum.ict.ieum.hospital.repository;

import com.ieum.ict.ieum.hospital.domain.Hospital;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    List<Hospital> findAllByNameContainingIgnoreCaseOrderByNameAsc(String keyword);
}
