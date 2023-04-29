package com.example.DigitalBankService.services;

import com.example.DigitalBankService.dtos.BankAccountDTO;
import com.example.DigitalBankService.entities.BankAccount;
import com.example.DigitalBankService.exceptions.AccountTypeException;
import com.example.DigitalBankService.exceptions.BankAccountNotFoundException;
import com.example.DigitalBankService.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    <T extends BankAccountDTO> T saveBankAccount(double initBalance, String accountType, Long customerId, double interestRateOrOverDraft) throws AccountTypeException, CustomerNotFoundException;

    <T extends BankAccountDTO> T getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<BankAccountDTO> getCustomerBankAccount(Long customerId)
            throws CustomerNotFoundException;
    List<BankAccountDTO> getBankAccounts();

}
