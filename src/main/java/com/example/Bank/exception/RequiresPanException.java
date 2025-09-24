package com.example.Bank.exception;

public class RequiresPanException extends RuntimeException {
    public RequiresPanException(String message) {
        super(message);
    }
}