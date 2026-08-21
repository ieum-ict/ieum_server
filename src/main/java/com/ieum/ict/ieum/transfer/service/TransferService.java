package com.ieum.ict.ieum.transfer.service;

import com.ieum.ict.ieum.auth.domain.User;
import com.ieum.ict.ieum.auth.repository.UserRepository;
import com.ieum.ict.ieum.transfer.api.TransferRequest;
import com.ieum.ict.ieum.transfer.api.TransferResponse;
import com.ieum.ict.ieum.transfer.domain.Transfer;
import com.ieum.ict.ieum.transfer.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final UserRepository userRepository;
    private final TransferRepository transferRepository;

    @Transactional
    public TransferResponse create(String email, TransferRequest.Create request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
        return TransferResponse.from(transferRepository.save(new Transfer(user, request.patientName(), request.patientAge(),
                request.symptom(), request.departureAddress())));
    }
}
