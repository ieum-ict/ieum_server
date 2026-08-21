package com.ieum.ict.ieum.transfer.repository;

import com.ieum.ict.ieum.transfer.domain.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {}
