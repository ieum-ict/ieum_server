package com.ieum.ict.ieum.hospital.service;

import com.ieum.ict.ieum.hospital.api.*;
import com.ieum.ict.ieum.hospital.domain.Hospital;
import com.ieum.ict.ieum.hospital.domain.HospitalResourceHistory;
import com.ieum.ict.ieum.hospital.repository.HospitalRepository;
import com.ieum.ict.ieum.hospital.repository.HospitalResourceHistoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class HospitalService {
    private final HospitalRepository hospitalRepository;
    private final HospitalResourceHistoryRepository historyRepository;

    @Transactional(readOnly = true)
    public List<HospitalResponse> findAll() {
        return hospitalRepository.findAll().stream().map(HospitalResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public HospitalResponse findById(Long hospitalId) {
        return HospitalResponse.from(getHospital(hospitalId));
    }

    @Transactional(readOnly = true)
    public List<HospitalResponse> search(HospitalRequest.Search request) {
        return hospitalRepository.findAllByNameContainingIgnoreCaseOrderByNameAsc(request.keyword()).stream()
                .map(HospitalResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public String findResources(Long hospitalId) {
        return getHospital(hospitalId).getResourcesContent();
    }

    @Transactional
    public HospitalResponse updateResources(Long hospitalId, HospitalRequest.ResourceUpdate request) {
        Hospital hospital = getHospital(hospitalId);
        hospital.updateResources(request.content());
        historyRepository.save(new HospitalResourceHistory(hospital, request.content()));
        return HospitalResponse.from(hospital);
    }

    @Transactional(readOnly = true)
    public List<HospitalResourceHistoryResponse> findResourceHistory(Long hospitalId) {
        Hospital hospital = getHospital(hospitalId);
        return historyRepository.findAllByHospitalOrderByChangedAtDesc(hospital).stream()
                .map(HospitalResourceHistoryResponse::from).toList();
    }

    private Hospital getHospital(Long hospitalId) {
        return hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "병원을 찾을 수 없습니다."));
    }
}
