package com.example.Bank.exception;

public class HighValueTransactionException extends RuntimeException {
    public HighValueTransactionException(String message) {
        super(message);
    }
}
