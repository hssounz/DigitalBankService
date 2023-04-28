package com.example.DigitalBankService.services;

import com.example.DigitalBankService.entities.BankAccount;
import com.example.DigitalBankService.entities.Customer;
import com.example.DigitalBankService.exceptions.AccountOperationsException;
import com.example.DigitalBankService.exceptions.AccountTypeException;
import com.example.DigitalBankService.exceptions.BankAccountNotFoundException;
import com.example.DigitalBankService.exceptions.CustomerNotFoundException;

import java.util.List;

public interface IBankAccountService {

    Customer saveCustomer(Customer customer);
    BankAccount saveBankAccount(double initBalance, String accountType, Long customerId, double interestRateOrOverDraft) throws AccountTypeException, CustomerNotFoundException;
    List<Customer> getCustomers();
    BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
    List<BankAccount> getCustomerBankAccount(Long customerId) throws CustomerNotFoundException;
    List<BankAccount> getBankAccounts();
    void depose(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void withdrawal(String accountId, double amount, String description) throws AccountOperationsException, BankAccountNotFoundException;
    void transfer(Long customerId, String accountIdSource, String accountIdDestination, double amount) throws AccountOperationsException, CustomerNotFoundException, BankAccountNotFoundException;

}
