package com.ieum.ict.ieum.transfer.repository;

import com.ieum.ict.ieum.transfer.domain.Transfer;
import com.ieum.ict.ieum.transfer.domain.TransferStatusHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferStatusHistoryRepository extends JpaRepository<TransferStatusHistory, Long> {
    List<TransferStatusHistory> findAllByTransferOrderByChangedAtAsc(Transfer transfer);
}
