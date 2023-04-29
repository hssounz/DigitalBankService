package com.example.DigitalBankService.mappers;

import com.example.DigitalBankService.dtos.*;
import com.example.DigitalBankService.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class BankEntitiesMapperImpl implements IBankEntitiesMapper {

    /**
     *
     * Customer entity DTO mapper
     *
     */

    @Override
    public CustomerDTO fromCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        return customerDTO;
    }
    @Override
    public Customer fromCustomerDto(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    /**
     *
     * BankAccount entity DTO mapper
     *
     */
    @Override
    public SavingsBankAccountDTO fromSavingAccount(SavingsAccount savingsAccount) {
        SavingsBankAccountDTO savingsBankAccountDTO = new SavingsBankAccountDTO();
        BeanUtils.copyProperties(savingsAccount, savingsBankAccountDTO);
        savingsBankAccountDTO.setCustomerDTO(
                fromCustomer(savingsAccount.getCustomer())
        );
        return savingsBankAccountDTO;
    }
    @Override
    public SavingsAccount fromSavingAccountDTO(SavingsBankAccountDTO savingsBankAccountDTO) {
        SavingsAccount savingsAccount = new SavingsAccount();
        BeanUtils.copyProperties(savingsBankAccountDTO, savingsAccount);
        savingsAccount.setCustomer(
                fromCustomerDto(savingsBankAccountDTO.getCustomerDTO())
        );
        return savingsAccount;
    }
    @Override
    public CurrentsBankAccountDTO fromCurrentAccount(CurrentsAccount currentsAccount) {
        CurrentsBankAccountDTO currentsBankAccountDTO = new CurrentsBankAccountDTO();
        BeanUtils.copyProperties(currentsAccount, currentsBankAccountDTO);
        currentsBankAccountDTO.setCustomerDTO(
                fromCustomer(currentsAccount.getCustomer())
        );
        return currentsBankAccountDTO;
    }
    @Override
    public CurrentsAccount fromCurrentAccountDTO(CurrentsBankAccountDTO currentsBankAccountDTO) {
        CurrentsAccount currentsAccount = new CurrentsAccount();
        BeanUtils.copyProperties(currentsBankAccountDTO, currentsAccount);
        currentsAccount.setCustomer(
                fromCustomerDto(currentsBankAccountDTO.getCustomerDTO())
        );
        return currentsAccount;
    }

    @Override
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation) {
        AccountOperationDTO accountOperationDTO = new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        return accountOperationDTO;
    }

    @Override
    public AccountHistoryDTO fromAccountOperationsPage(Page<AccountOperation> accountOperations, BankAccount bankAccount) {
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();

        accountHistoryDTO.setAccountOperationDTOS(
                accountOperations
                        .getContent()
                        .stream()
                        .map(accountOperation -> this.fromAccountOperation(accountOperation)
                        )
                        .collect(Collectors.toList())
        );

        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setPageSize(accountOperations.getSize());
        accountHistoryDTO.setCurrentPage(accountOperations.getNumber());
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());

        return accountHistoryDTO;
    }
}
