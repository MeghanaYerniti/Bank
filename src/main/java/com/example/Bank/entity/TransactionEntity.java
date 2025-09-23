package com.example.Bank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Transaction")
public class TransactionEntity {

    @Id
    @Column(name = "transaction_id")
    private UUID transactionId;

    @Column(name = "from_account_id")
    private long fromAccountId;

    @Column(name = "to_account_id")
    private long toAccountId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "initial_balance")
    private double InitialBalance;

    @Column(name = "remaining_balance")
    private double remainingBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private Transcationtype transactionType;

    @Column(name= "timestamp")
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    private Status status;
}