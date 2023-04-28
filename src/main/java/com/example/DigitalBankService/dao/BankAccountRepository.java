package com.example.DigitalBankService.dao;

import com.example.DigitalBankService.entities.BankAccount;
import com.example.DigitalBankService.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BankAccountRepository extends JpaRepository<BankAccount, String > {
    List<BankAccount> findByCustomer(Customer customer);
}
