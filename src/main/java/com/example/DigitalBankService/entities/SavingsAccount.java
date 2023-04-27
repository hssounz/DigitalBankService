package com.example.DigitalBankService.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
@DiscriminatorValue("SA")
public class SavingsAccount extends BankAccount{
    private double interestRate;
}
