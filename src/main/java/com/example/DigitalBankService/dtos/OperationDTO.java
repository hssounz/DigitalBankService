package com.example.DigitalBankService.dtos;

import lombok.Data;

@Data
public class OperationDTO {
    private String accountId;
    private double amount;
    private String description;
}
