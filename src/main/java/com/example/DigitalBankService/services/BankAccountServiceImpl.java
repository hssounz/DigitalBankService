package com.example.DigitalBankService.services;

import com.example.DigitalBankService.dao.AccountOperationRepository;
import com.example.DigitalBankService.dao.BankAccountRepository;
import com.example.DigitalBankService.dao.CustomerRepository;
import com.example.DigitalBankService.dtos.BankAccountDTO;
import com.example.DigitalBankService.dtos.CurrentsBankAccountDTO;
import com.example.DigitalBankService.dtos.SavingsBankAccountDTO;
import com.example.DigitalBankService.entities.*;
import com.example.DigitalBankService.enums.AccountStatus;
import com.example.DigitalBankService.exceptions.*;
import com.example.DigitalBankService.mappers.IBankEntitiesMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountRepository bankAccountRepository;
    private CustomerRepository customerRepository;
    private AccountOperationRepository accountOperationRepository;
    private IBankEntitiesMapper dtoMapper;

    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository, CustomerRepository customerRepository, AccountOperationRepository accountOperationRepository, IBankEntitiesMapper bankEntitiesMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.customerRepository = customerRepository;
        this.accountOperationRepository = accountOperationRepository;
        this.dtoMapper = bankEntitiesMapper;
    }


    @Override
    public <T extends BankAccountDTO> T saveBankAccount(double initBalance, String accountType, Long customerId, double interestRateOrOverDraft) throws AccountTypeException, CustomerNotFoundException {
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
        {
            ((CurrentsAccount) bankAccount).setOverDraft(interestRateOrOverDraft);
            CurrentsBankAccountDTO currentsBankAccountDTO = dtoMapper.fromCurrentAccount((CurrentsAccount) bankAccount);
            return (T) currentsBankAccountDTO;
        }
        else
        {
            ((SavingsAccount) bankAccount).setInterestRate(interestRateOrOverDraft);
            SavingsBankAccountDTO savingsBankAccountDTO = dtoMapper.fromSavingAccount((SavingsAccount) bankAccount);
            return (T) savingsBankAccountDTO;
        }

    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {

        BankAccount bankAccount = bankAccountRepository.findById(accountId).orElseThrow(BankAccountNotFoundException::new);
        if (bankAccount instanceof SavingsAccount)
            return dtoMapper.fromSavingAccount((SavingsAccount) bankAccount);
        else
            return dtoMapper.fromCurrentAccount((CurrentsAccount) bankAccount);
    }

    @Override
    public List<BankAccountDTO> getCustomerBankAccount(Long customerId) throws CustomerNotFoundException {
//        return bankAccountRepository
//                .findByCustomer(customerRepository
//                        .findById(customerId)
//                        .orElseThrow(CustomerNotFoundException::new)
//                )
//                .stream()
//                .map(account -> {
//                    return account instanceof CurrentsAccount
//                            ? dtoMapper.fromCurrentAccount((CurrentsAccount) account)
//                            : dtoMapper.fromSavingAccount((SavingsAccount) account);
//                })
//                .collect(Collectors.toList());

        return customerRepository
                .findById(customerId)
                .orElseThrow(CustomerNotFoundException::new)
                .getBankAccounts()
                .stream()
                .map(account -> {
                    return account instanceof CurrentsAccount
                            ? dtoMapper.fromCurrentAccount((CurrentsAccount) account)
                            : dtoMapper.fromSavingAccount((SavingsAccount) account);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<BankAccountDTO> getBankAccounts() {
        return bankAccountRepository
                .findAll()
                .stream()
                .map(account -> {
            return account instanceof CurrentsAccount
                    ? dtoMapper.fromCurrentAccount((CurrentsAccount) account)
                    : dtoMapper.fromSavingAccount((SavingsAccount) account);
        })
                .collect(Collectors.toList());
    }

}
