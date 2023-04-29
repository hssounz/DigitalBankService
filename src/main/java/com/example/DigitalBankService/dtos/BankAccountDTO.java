package com.example.DigitalBankService.dtos;

import com.example.DigitalBankService.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
@AllArgsConstructor
public class BankAccountDTO {
    private String type =
            this.getClass().getSimpleName()
                    .equals("CurrentsBankAccountDTO")
                    ? "Current Account"
                    : "Savings Account";
    private String id;
    private double balance;
    private LocalDateTime createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;

}
