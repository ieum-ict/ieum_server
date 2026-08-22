package com.ieum.ict.ieum.request.service;

import com.ieum.ict.ieum.auth.domain.User;
import com.ieum.ict.ieum.auth.repository.UserRepository;
import com.ieum.ict.ieum.request.api.AcceptanceRequestRequest;
import com.ieum.ict.ieum.request.api.AcceptanceRequestResponse;
import com.ieum.ict.ieum.request.domain.AcceptanceRequest;
import com.ieum.ict.ieum.request.domain.AcceptanceRequestStatus;
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
public class AcceptanceRequestService {
    private final AcceptanceRequestRepository requestRepository;
    private final UserRepository userRepository;
    private final TransferRepository transferRepository;

    @Transactional
    public AcceptanceRequestResponse create(String email, AcceptanceRequestRequest.Create request) {
        User user = getUser(email);
        Transfer transfer = getOwnedTransfer(email, request.transferId());
        return AcceptanceRequestResponse.from(requestRepository.save(
                new AcceptanceRequest(transfer, user, request.hospitalId(), request.content())));
    }

    @Transactional
    public List<AcceptanceRequestResponse> createBulk(String email, AcceptanceRequestRequest.BulkCreate request) {
        User user = getUser(email);
        Transfer transfer = getOwnedTransfer(email, request.transferId());
        return request.hospitalIds().stream().map(hospitalId -> requestRepository.save(
                new AcceptanceRequest(transfer, user, hospitalId, request.content())))
                .map(AcceptanceRequestResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<AcceptanceRequestResponse> findAll(String email) {
        return requestRepository.findAllByRequesterEmailOrderByCreatedAtDesc(email).stream()
                .map(AcceptanceRequestResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public AcceptanceRequestResponse findById(String email, Long requestId) {
        return AcceptanceRequestResponse.from(getOwnedRequest(email, requestId));
    }

    @Transactional
    public AcceptanceRequestResponse respond(String email, Long requestId, AcceptanceRequestRequest.Response request) {
        AcceptanceRequest acceptanceRequest = getOwnedRequest(email, requestId);
        if (request.status() == AcceptanceRequestStatus.REQUESTED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "응답 상태는 REQUESTED일 수 없습니다.");
        }
        acceptanceRequest.respond(request.status(), request.content());
        return AcceptanceRequestResponse.from(acceptanceRequest);
    }

    @Transactional
    public AcceptanceRequestResponse retry(String email, Long requestId) {
        AcceptanceRequest acceptanceRequest = getOwnedRequest(email, requestId);
        acceptanceRequest.retry();
        return AcceptanceRequestResponse.from(acceptanceRequest);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }

    private Transfer getOwnedTransfer(String email, Long transferId) {
        return transferRepository.findById(transferId)
                .filter(transfer -> transfer.getRequester().getEmail().equals(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이송 요청을 찾을 수 없습니다."));
    }

    private AcceptanceRequest getOwnedRequest(String email, Long requestId) {
        return requestRepository.findById(requestId)
                .filter(request -> request.getRequester().getEmail().equals(email))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "수용 요청을 찾을 수 없습니다."));
    }
}
