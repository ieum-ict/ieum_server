package com.ieum.ict.ieum.admin.service;

import com.ieum.ict.ieum.admin.api.*;
import com.ieum.ict.ieum.auth.domain.User;
import com.ieum.ict.ieum.auth.repository.UserRepository;
import com.ieum.ict.ieum.request.repository.AcceptanceRequestRepository;
import com.ieum.ict.ieum.request.domain.AcceptanceRequest;
import com.ieum.ict.ieum.request.api.AcceptanceRequestResponse;
import com.ieum.ict.ieum.hospital.api.HospitalResponse;
import com.ieum.ict.ieum.hospital.domain.Hospital;
import com.ieum.ict.ieum.hospital.repository.HospitalRepository;
import com.ieum.ict.ieum.transfer.domain.Transfer;
import com.ieum.ict.ieum.transfer.domain.TransferStatus;
import com.ieum.ict.ieum.transfer.repository.TransferRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final TransferRepository transferRepository;
    private final AcceptanceRequestRepository acceptanceRequestRepository;
    private final HospitalRepository hospitalRepository;

    @Transactional(readOnly = true)
    public List<AdminUserResponse> findUsers() {
        return userRepository.findAll().stream().map(AdminUserResponse::from).toList();
    }

    @Transactional
    public AdminUserResponse updateUser(Long userId, AdminRequest.UserUpdate request) {
        User user = getUser(userId);
        user.updateRole(request.role());
        return AdminUserResponse.from(user);
    }

    @Transactional
    public void deleteUser(Long userId) { userRepository.delete(getUser(userId)); }

    @Transactional(readOnly = true)
    public List<Transfer> transferHistory() { return transferRepository.findAll(); }

    @Transactional(readOnly = true)
    public List<Transfer> findTransfers() { return transferRepository.findAll(); }

    @Transactional
    public Transfer updateTransferStatus(Long transferId, TransferStatus status) {
        Transfer transfer = transferRepository.findById(transferId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이송 요청을 찾을 수 없습니다."));
        transfer.updateStatus(status);
        return transfer;
    }

    @Transactional(readOnly = true)
    public List<AcceptanceRequestResponse> findAcceptanceRequests() {
        return acceptanceRequestRepository.findAll().stream().map(AcceptanceRequestResponse::from).toList();
    }

    @Transactional
    public AcceptanceRequestResponse respondAcceptanceRequest(Long requestId, AdminRequest.AcceptanceResponse request) {
        AcceptanceRequest acceptanceRequest = acceptanceRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "수용 요청을 찾을 수 없습니다."));
        acceptanceRequest.respond(request.status(), request.content());
        return AcceptanceRequestResponse.from(acceptanceRequest);
    }

    @Transactional(readOnly = true)
    public List<HospitalResponse> findHospitals() {
        return hospitalRepository.findAll().stream().map(HospitalResponse::from).toList();
    }

    @Transactional
    public HospitalResponse updateHospital(Long hospitalId, AdminRequest.HospitalUpdate request) {
        Hospital hospital = hospitalRepository.findById(hospitalId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "병원을 찾을 수 없습니다."));
        hospital.update(request.name(), request.address(), request.phone());
        return HospitalResponse.from(hospital);
    }

    @Transactional
    public void deleteHospital(Long hospitalId) {
        if (!hospitalRepository.existsById(hospitalId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "병원을 찾을 수 없습니다.");
        }
        hospitalRepository.deleteById(hospitalId);
    }

    @Transactional(readOnly = true)
    public AdminStatsResponse stats() {
        return new AdminStatsResponse(userRepository.count(), transferRepository.count(), acceptanceRequestRepository.count());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }
}
