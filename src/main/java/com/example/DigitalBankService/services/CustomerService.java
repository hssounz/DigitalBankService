package com.example.DigitalBankService.services;

import com.example.DigitalBankService.dtos.CustomerDTO;
import com.example.DigitalBankService.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CustomerDTO updateCustomer(CustomerDTO customerDTO);
    void deleteCustomer(Long customerId);
    List<CustomerDTO> getCustomers();
    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;
}
