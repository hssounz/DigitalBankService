package com.example.DigitalBankService.dtos;

import lombok.Data;

@Data
public class TransferOperationDTO extends OperationDTO{
    private String destAccountId;
}
