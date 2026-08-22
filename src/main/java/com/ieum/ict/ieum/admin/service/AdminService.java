package com.ieum.ict.ieum.admin.service;

import com.ieum.ict.ieum.admin.api.*;
import com.ieum.ict.ieum.auth.domain.User;
import com.ieum.ict.ieum.auth.repository.UserRepository;
import com.ieum.ict.ieum.request.repository.AcceptanceRequestRepository;
import com.ieum.ict.ieum.transfer.domain.Transfer;
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
    public AdminStatsResponse stats() {
        return new AdminStatsResponse(userRepository.count(), transferRepository.count(), acceptanceRequestRepository.count());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }
}
