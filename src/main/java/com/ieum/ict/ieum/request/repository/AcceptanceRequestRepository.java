package com.ieum.ict.ieum.request.repository;

import com.ieum.ict.ieum.request.domain.AcceptanceRequest;
import com.ieum.ict.ieum.transfer.domain.Transfer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcceptanceRequestRepository extends JpaRepository<AcceptanceRequest, Long> {
    List<AcceptanceRequest> findAllByRequesterEmailOrderByCreatedAtDesc(String email);
    List<AcceptanceRequest> findAllByTransferAndRequesterEmailOrderByCreatedAtDesc(Transfer transfer, String email);
}
