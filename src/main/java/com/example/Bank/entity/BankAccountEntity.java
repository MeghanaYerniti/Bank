package com.example.Bank.entity;

import com.example.Bank.enums.AccountStatus;
import com.example.Bank.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BankAccount")
public class BankAccountEntity {

    @Id
    @Column(name = "account_id")
    private long accountId;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore // to resolve classic "infinite recursion problem" while GetMapping
    private CustomerEntity customer;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "balance")
    private double balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AccountType type;

    @Column(name = "interest_rate")
    private double interestRate;

    @Column(name = "last_transaction_timestamp")
    private LocalDateTime lastTransactionTimestamp;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status")
    private AccountStatus accountStatus;
}