package com.example.DigitalBankService.mappers;

import com.example.DigitalBankService.dtos.*;
import com.example.DigitalBankService.entities.*;
import org.springframework.data.domain.Page;

public interface IBankEntitiesMapper {
    CustomerDTO fromCustomer(Customer customer);
    Customer fromCustomerDto(CustomerDTO customerDTO);

    SavingsBankAccountDTO fromSavingAccount(SavingsAccount savingsAccount);
    SavingsAccount fromSavingAccountDTO(SavingsBankAccountDTO savingsBankAccountDTO);
    CurrentsBankAccountDTO fromCurrentAccount(CurrentsAccount currentsAccount);
    CurrentsAccount fromCurrentAccountDTO(CurrentsBankAccountDTO currentsBankAccountDTO);
    AccountOperationDTO fromAccountOperation(AccountOperation accountOperation);
    AccountHistoryDTO fromAccountOperationsPage(Page<AccountOperation> accountOperations, BankAccount bankAccount);
}
