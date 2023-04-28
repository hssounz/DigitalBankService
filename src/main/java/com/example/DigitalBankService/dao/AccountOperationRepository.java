package com.example.DigitalBankService.dao;

import com.example.DigitalBankService.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findById(String accountId);
    Page<AccountOperation> findByIdOrderByOperationDateDesc(String accountId, Pageable pageable);
}
