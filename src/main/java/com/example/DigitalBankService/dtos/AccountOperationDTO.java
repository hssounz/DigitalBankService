package com.example.DigitalBankService.dtos;

import com.example.DigitalBankService.entities.BankAccount;
import com.example.DigitalBankService.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperationDTO {
    private Long id;
    private LocalDateTime operationDate;
    private double amount;
    private OperationType type;
    private String description;
}
