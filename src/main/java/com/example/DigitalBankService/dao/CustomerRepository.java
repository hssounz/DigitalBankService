package com.example.DigitalBankService.dao;

import com.example.DigitalBankService.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByFullNameContaining(String keyWord);
}
