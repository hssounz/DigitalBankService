package com.example.DigitalBankService.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
@DiscriminatorValue("CA")
public class CurrentsAccount extends BankAccount{
    private double overDraft;
}
