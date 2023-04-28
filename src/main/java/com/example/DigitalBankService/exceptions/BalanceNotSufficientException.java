package com.example.DigitalBankService.exceptions;

public class BalanceNotSufficientException extends Exception{
    public BalanceNotSufficientException(String message) {
        super(message);
    }
}
