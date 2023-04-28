package com.example.DigitalBankService.services;

import com.example.DigitalBankService.dao.AccountOperationRepository;
import com.example.DigitalBankService.dao.BankAccountRepository;
import com.example.DigitalBankService.dao.CustomerRepository;
import com.example.DigitalBankService.entities.*;
import com.example.DigitalBankService.enums.AccountStatus;
import com.example.DigitalBankService.enums.OperationType;
import com.example.DigitalBankService.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
public class BankAccountServiceImpl implements IBankAccountService {

    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository, AccountOperationRepository accountOperationRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.accountOperationRepository = accountOperationRepository;
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        log.info("Saving a new customer");
        return customerRepository.save(customer);
    }

    @Override
    public BankAccount saveBankAccount(double initBalance, String accountType, Long customerId, double interestRateOrOverDraft) throws AccountTypeException, CustomerNotFoundException {
        BankAccount bankAccount =
                accountType.equals("current")
                ? new CurrentsAccount()
                : accountType.equals("savings")
                ? new SavingsAccount() : null;

        if (bankAccount == null)
            throw new AccountTypeException("This is not a valid account type: " + accountType);

        bankAccount.setStatus(AccountStatus.CREATED);
        bankAccount.setBalance(initBalance);
        bankAccount.setCustomer(customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new));
        if (bankAccount instanceof CurrentsAccount)
            ((CurrentsAccount) bankAccount).setOverDraft(interestRateOrOverDraft);
        else
            ((SavingsAccount) bankAccount).setInterestRate(interestRateOrOverDraft);

        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
        return bankAccountRepository.findById(accountId).orElseThrow(BankAccountNotFoundException::new);
    }

    @Override
    public List<BankAccount> getCustomerBankAccount(Long customerId) throws CustomerNotFoundException {
        return bankAccountRepository
                .findByCustomer(customerRepository
                        .findById(customerId)
                        .orElseThrow(CustomerNotFoundException::new)
                );
    }

    @Override
    public List<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
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
}
