package com.example.Bank.dto;

import com.example.Bank.enums.AccountStatus;
import com.example.Bank.enums.AccountType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BankAccountDTO {

    @Positive
    @Min(value = 10000000, message = "Account ID must be at least 8 digits")
    private long accountId;

    private CustomerDTO customer;

    @NotBlank(message = "Account holder name is required")
    @Size(min = 5, max = 50, message = "Account holder name must be between 5 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Account holder name must not contain numbers or special characters")
    private String accountHolderName;

    @PositiveOrZero(message = "Balance must be non-negative")
    private double balance;

    private AccountType type;

    @PositiveOrZero(message = "Interest rate must be non-negative")
    private double interestRate;

    private LocalDateTime lastTransactionTimestamp;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull(message = "Account status is required")
    private AccountStatus accountStatus;
}