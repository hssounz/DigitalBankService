package com.example.DigitalBankService.dao;

import com.example.DigitalBankService.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {
    List<AccountOperation> findByBankAccountId(String accountId);
    Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);
//    Page<AccountOperation> findByIdOrderByOperationDateDesc(String accountId, Pageable pageable);
}
