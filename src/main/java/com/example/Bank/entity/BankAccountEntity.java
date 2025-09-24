package com.example.Bank.entity;

import com.example.Bank.enums.AccountStatus;
import com.example.Bank.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="BankAccount")
public class BankAccountEntity {

    @Id
    @Column(name = "account_id")
    @Min(value = 10000000, message = "Account ID must be at least 8 digits")
    private long accountId;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore // to resolve classic "infinite recursion problem" while GetMapping
    private CustomerEntity customer;

    @Column(name = "account_holder_name")
    @NotBlank(message = "Account holder name is required")
    @Size(min = 5, max = 50, message = "Account holder name must be 5 and 50 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Account holder name must not contain numbers or special characters")
    private String accountHolderName;

    @Column(name = "balance")
    @PositiveOrZero
    private double balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AccountType type;

    @Column(name="interest_rate")
    private double interestRate;

    @Column(name="last_transaction_timestamp")
    private LocalDateTime lastTransactionTimestamp;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="update_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name="account_status")
    @NotNull(message = "Account status is required")
    private AccountStatus accountStatus;


}
