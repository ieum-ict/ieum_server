package com.ieum.ict.ieum.transfer.repository;

import com.ieum.ict.ieum.transfer.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findAllByRequesterEmailOrderByCreatedAtDesc(String email);
}
