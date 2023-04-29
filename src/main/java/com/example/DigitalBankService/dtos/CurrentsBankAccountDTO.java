package com.example.DigitalBankService.dtos;

import com.example.DigitalBankService.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class CurrentsBankAccountDTO extends BankAccountDTO{
    private double overDraft;
}
