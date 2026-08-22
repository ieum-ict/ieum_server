package com.ieum.ict.ieum.transfer.repository;

import com.ieum.ict.ieum.transfer.domain.Transfer;
import com.ieum.ict.ieum.transfer.domain.TransferRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRecordRepository extends JpaRepository<TransferRecord, Long> {
    List<TransferRecord> findAllByTransferAndTypeOrderByCreatedAtDesc(Transfer transfer, String type);
}
