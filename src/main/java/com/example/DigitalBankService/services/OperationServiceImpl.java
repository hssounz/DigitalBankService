package com.example.DigitalBankService.services;

import com.example.DigitalBankService.dao.AccountOperationRepository;
import com.example.DigitalBankService.dao.BankAccountRepository;
import com.example.DigitalBankService.dao.CustomerRepository;
import com.example.DigitalBankService.dtos.AccountHistoryDTO;
import com.example.DigitalBankService.dtos.AccountOperationDTO;
import com.example.DigitalBankService.entities.AccountOperation;
import com.example.DigitalBankService.entities.BankAccount;
import com.example.DigitalBankService.enums.OperationType;
import com.example.DigitalBankService.exceptions.*;
import com.example.DigitalBankService.mappers.IBankEntitiesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service @Slf4j
public class OperationServiceImpl implements OperationService {

    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private IBankEntitiesMapper dtoMapper;

    public OperationServiceImpl(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository, AccountOperationRepository accountOperationRepository, IBankEntitiesMapper dtoMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.accountOperationRepository = accountOperationRepository;
        this.dtoMapper = dtoMapper;
    }

    @Override
    public void depose(String accountId, double amount, String description) throws BankAccountNotFoundException {

        BankAccount bankAccount =
                bankAccountRepository
                        .findById(accountId)
                        .orElseThrow(BankAccountNotFoundException::new);

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setType(OperationType.DEPOSE);
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance() + amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void withdrawal(String accountId, double amount, String description) throws AccountOperationsException, BankAccountNotFoundException {

        BankAccount bankAccount =
                bankAccountRepository
                        .findById(accountId)
                        .orElseThrow(BankAccountNotFoundException::new);

        if(bankAccount.getBalance() < amount)
            throw new AccountOperationsException(
                    new BalanceNotSufficientException("Invalid amount, You don't have enough cash.")
            );

        AccountOperation accountOperation = new AccountOperation();
        accountOperation.setAmount(amount);
        accountOperation.setType(OperationType.WITHDRAWL);
        accountOperation.setDescription(description);
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepository.save(accountOperation);

        bankAccount.setBalance(bankAccount.getBalance() - amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void transfer(Long customerId, String accountIdSource, String accountIdDestination, double amount) throws AccountOperationsException, CustomerNotFoundException, BankAccountNotFoundException {

        if (!customerRepository
                .findById(customerId)
                .orElseThrow(CustomerNotFoundException::new
                ).equals(
                        bankAccountRepository
                                .findById(accountIdSource)
                                .orElseThrow(BankAccountNotFoundException::new)
                                .getCustomer()
                ))
            throw new AccountOperationsException(
                    new CustomerMisMatchException("This bank account does not belong to this customer")
            );

        this.withdrawal(accountIdSource, amount, "Transfer To " + accountIdDestination);
        depose(accountIdDestination, amount, "Transfer from " + accountIdSource);
    }

    @Override
    public List<AccountOperationDTO> getAccountHistory(String accountId) {
        return accountOperationRepository
                .findByBankAccountId(accountId)
                .stream()
                .map(
                        accountOperation -> dtoMapper.fromAccountOperation(accountOperation)
                )
                .collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {

        BankAccount bankAccount = bankAccountRepository
                .findById(accountId)
                .orElseThrow(BankAccountNotFoundException::new);

        Page<AccountOperation> accountOperations = accountOperationRepository
                .findByBankAccountId(
                        accountId,
                        PageRequest.of(page, size)
                );
        return dtoMapper.fromAccountOperationsPage(accountOperations, bankAccount);
    }

}
