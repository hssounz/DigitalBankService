package com.example.DigitalBankService.entities;

import com.example.DigitalBankService.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "operation_date")
    private LocalDateTime operationDate;

    private double amount;

    @Enumerated(EnumType.STRING)
    private OperationType type;

    @ManyToOne
    private BankAccount bankAccount;
    @Column(nullable = true)
    private String description;

    @PrePersist
    protected void onCreate() {
        this.operationDate = LocalDateTime.now();
    }



}
