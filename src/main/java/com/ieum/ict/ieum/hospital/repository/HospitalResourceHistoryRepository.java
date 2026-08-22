package com.ieum.ict.ieum.hospital.repository;

import com.ieum.ict.ieum.hospital.domain.Hospital;
import com.ieum.ict.ieum.hospital.domain.HospitalResourceHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalResourceHistoryRepository extends JpaRepository<HospitalResourceHistory, Long> {
    List<HospitalResourceHistory> findAllByHospitalOrderByChangedAtDesc(Hospital hospital);
}
