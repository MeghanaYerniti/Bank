package com.example.Bank.dto;

import com.example.Bank.enums.Status;
import com.example.Bank.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionDTO {

    private UUID transactionId;

    private Long fromAccountId;

    private Long toAccountId;

    @Positive(message = "Amount must be greater than 0")
    private double amount;

    private double initialBalance;

    private double remainingBalance;

    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;

    private LocalDateTime timestamp;

    @NotNull(message = "Status is required")
    private Status status;
}