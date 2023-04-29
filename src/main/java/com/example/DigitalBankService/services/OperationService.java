package com.example.DigitalBankService.services;


import com.example.DigitalBankService.dtos.AccountHistoryDTO;
import com.example.DigitalBankService.dtos.AccountOperationDTO;
import com.example.DigitalBankService.entities.AccountOperation;
import com.example.DigitalBankService.exceptions.AccountOperationsException;
import com.example.DigitalBankService.exceptions.BankAccountNotFoundException;
import com.example.DigitalBankService.exceptions.CustomerNotFoundException;

import java.util.List;

public interface OperationService {
    void depose(String accountId, double amount, String description)
            throws BankAccountNotFoundException;
    void withdrawal(String accountId, double amount, String description)
            throws AccountOperationsException, BankAccountNotFoundException;
    void transfer(Long customerId, String accountIdSource, String accountIdDestination, double amount)
            throws AccountOperationsException, CustomerNotFoundException, BankAccountNotFoundException;
    List<AccountOperationDTO> getAccountHistory(String accountId);
    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
