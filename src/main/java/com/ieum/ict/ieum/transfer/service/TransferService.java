package com.ieum.ict.ieum.transfer.service;

import com.ieum.ict.ieum.auth.domain.User;
import com.ieum.ict.ieum.auth.repository.UserRepository;
import com.ieum.ict.ieum.transfer.api.TransferRequest;
import com.ieum.ict.ieum.transfer.api.TransferResponse;
import com.ieum.ict.ieum.transfer.domain.Transfer;
import com.ieum.ict.ieum.transfer.domain.TransferStatus;
import com.ieum.ict.ieum.transfer.domain.TransferStatusHistory;
import com.ieum.ict.ieum.transfer.api.TransferHistoryResponse;
import com.ieum.ict.ieum.transfer.repository.TransferStatusHistoryRepository;
import com.ieum.ict.ieum.transfer.domain.TransferRecord;
import com.ieum.ict.ieum.transfer.api.TransferRecordResponse;
import com.ieum.ict.ieum.transfer.repository.TransferRecordRepository;
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
    private final TransferStatusHistoryRepository historyRepository;
    private final TransferRecordRepository recordRepository;

    @Transactional
    public TransferResponse create(String email, TransferRequest.Create request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
        Transfer transfer = transferRepository.save(new Transfer(user, request.patientName(), request.patientAge(),
                request.symptom(), request.departureAddress()));
        historyRepository.save(new TransferStatusHistory(transfer, TransferStatus.REQUESTED));
        return TransferResponse.from(transfer);
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
        historyRepository.save(new TransferStatusHistory(transfer, TransferStatus.CANCELLED));
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
        historyRepository.save(new TransferStatusHistory(transfer, status));
        return TransferResponse.from(transfer);
    }

    @Transactional
    public TransferResponse start(String email, Long transferId) {
        return move(email, transferId, Transfer::start);
    }

    @Transactional
    public TransferResponse arrive(String email, Long transferId) {
        return move(email, transferId, Transfer::arrive);
    }

    @Transactional
    public TransferResponse handover(String email, Long transferId) {
        return move(email, transferId, Transfer::handover);
    }

    private TransferResponse move(String email, Long transferId, java.util.function.Consumer<Transfer> action) {
        Transfer transfer = getOwnedTransfer(email, transferId);
        try {
            action.accept(transfer);
        } catch (IllegalStateException exception) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, exception.getMessage());
        }
        historyRepository.save(new TransferStatusHistory(transfer, transfer.getStatus()));
        return TransferResponse.from(transfer);
    }

    @Transactional(readOnly = true)
    public List<TransferHistoryResponse> findHistory(String email, Long transferId) {
        Transfer transfer = getOwnedTransfer(email, transferId);
        return historyRepository.findAllByTransferOrderByChangedAtAsc(transfer).stream()
                .map(TransferHistoryResponse::from).toList();
    }

    @Transactional
    public TransferRecordResponse saveRecord(String email, Long transferId, String type, String content) {
        Transfer transfer = getOwnedTransfer(email, transferId);
        return TransferRecordResponse.from(recordRepository.save(new TransferRecord(transfer, type, content)));
    }

    @Transactional(readOnly = true)
    public List<TransferRecordResponse> findRecords(String email, Long transferId, String type) {
        Transfer transfer = getOwnedTransfer(email, transferId);
        return recordRepository.findAllByTransferAndTypeOrderByCreatedAtDesc(transfer, type).stream()
                .map(TransferRecordResponse::from).toList();
    }

    private Transfer getOwnedTransfer(String email, Long transferId) {
        return transferRepository.findById(transferId)
                .filter(candidate -> candidate.getRequester().getEmail().equals(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이송 요청을 찾을 수 없습니다."));
    }
}
