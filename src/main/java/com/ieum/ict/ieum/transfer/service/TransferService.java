package com.ieum.ict.ieum.transfer.service;

import com.ieum.ict.ieum.auth.domain.User;
import com.ieum.ict.ieum.auth.repository.UserRepository;
import com.ieum.ict.ieum.transfer.api.TransferRequest;
import com.ieum.ict.ieum.transfer.api.TransferResponse;
import com.ieum.ict.ieum.transfer.domain.Transfer;
import com.ieum.ict.ieum.transfer.domain.TransferStatus;
import com.ieum.ict.ieum.transfer.repository.TransferRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
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

    @Transactional(readOnly = true)
    public List<TransferResponse> findAll(String email) {
        return transferRepository.findAllByRequesterEmailOrderByCreatedAtDesc(email).stream()
                .map(TransferResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TransferResponse findById(String email, Long transferId) {
        Transfer transfer = transferRepository.findById(transferId)
                .filter(candidate -> candidate.getRequester().getEmail().equals(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이송 요청을 찾을 수 없습니다."));
        return TransferResponse.from(transfer);
    }

    @Transactional
    public TransferResponse update(String email, Long transferId, TransferRequest.Update request) {
        Transfer transfer = getOwnedTransfer(email, transferId);
        if (transfer.getStatus() == com.ieum.ict.ieum.transfer.domain.TransferStatus.CANCELLED) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "취소된 이송 요청은 수정할 수 없습니다.");
        }
        transfer.update(request.patientName(), request.patientAge(), request.symptom(), request.departureAddress());
        return TransferResponse.from(transfer);
    }

    @Transactional
    public void cancel(String email, Long transferId) {
        Transfer transfer = getOwnedTransfer(email, transferId);
        transfer.cancel();
    }

    @Transactional(readOnly = true)
    public TransferStatus findStatus(String email, Long transferId) {
        return getOwnedTransfer(email, transferId).getStatus();
    }

    @Transactional
    public TransferResponse updateStatus(String email, Long transferId, TransferStatus status) {
        Transfer transfer = getOwnedTransfer(email, transferId);
        try {
            transfer.updateStatus(status);
        } catch (IllegalStateException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage());
        }
        return TransferResponse.from(transfer);
    }

    private Transfer getOwnedTransfer(String email, Long transferId) {
        return transferRepository.findById(transferId)
                .filter(candidate -> candidate.getRequester().getEmail().equals(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이송 요청을 찾을 수 없습니다."));
    }
}
