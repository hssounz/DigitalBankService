package com.example.DigitalBankService.web;

import com.example.DigitalBankService.dtos.*;
import com.example.DigitalBankService.enums.OperationType;
import com.example.DigitalBankService.exceptions.AccountOperationsException;
import com.example.DigitalBankService.exceptions.BankAccountNotFoundException;
import com.example.DigitalBankService.exceptions.CustomerNotFoundException;
import com.example.DigitalBankService.services.BankAccountService;
import com.example.DigitalBankService.services.OperationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BankAccountController {
    private BankAccountService bankAccountService;
    private OperationService operationService;

    public BankAccountController(BankAccountService bankAccountService, OperationService operationService) {
        this.bankAccountService = bankAccountService;
        this.operationService = operationService;
    }

    @GetMapping("/accounts/{id}")
    public BankAccountDTO getBankAccount(@PathVariable(name = "id") String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }
    @GetMapping("/accounts")
    public List<BankAccountDTO> listBankAccounts() {
        return bankAccountService.getBankAccounts();
    }
    @GetMapping("/accounts/{id}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable(name = "id") String accountId) {
        return operationService.getAccountHistory(accountId);
    }
    @GetMapping("/accounts/{id}/pageOperations")
    public AccountHistoryDTO getHistoryPage(
            @PathVariable(name = "id") String accountId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size
    ) throws BankAccountNotFoundException {
        return operationService.getAccountHistory(accountId, page, size);
    }

    @PostMapping("/accounts/operation/{type}")
    public OperationDTO operation(
            @PathVariable(name = "type")OperationType type,
            @RequestBody OperationDTO operationDTO
    ) throws BankAccountNotFoundException, AccountOperationsException {
        if (type.equals(OperationType.DEPOSE))
        operationService.depose(
                operationDTO.getAccountId(),
                operationDTO.getAmount(),
                operationDTO.getDescription()
        );
        else
            operationService.withdrawal(
                    operationDTO.getAccountId(),
                    operationDTO.getAmount(),
                    operationDTO.getDescription()
            );
        return operationDTO;
    }

    @PostMapping("/accounts/operation/transfer/{id}")
    public TransferOperationDTO transfer(
            @PathVariable(name = "id") Long customerId,
            @RequestBody TransferOperationDTO transferOperationDTO
    ) throws BankAccountNotFoundException, AccountOperationsException, CustomerNotFoundException {
        operationService.transfer(
                customerId,
                transferOperationDTO.getAccountId(),
                transferOperationDTO.getDestAccountId(),
                transferOperationDTO.getAmount()
        );
    }


}
