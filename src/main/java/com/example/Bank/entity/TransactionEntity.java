package com.example.Bank.entity;

import com.example.Bank.enums.Status;
import com.example.Bank.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Positive;
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
    // @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transactionId;

    @Column(name = "from_account_id")
    private Long fromAccountId;

    @Column(name = "to_account_id")
    private Long toAccountId;

    @Column(name = "amount")
    @Positive(message = "Amount must be greater than 0")
    private double amount;

    @Column(name = "initial_balance")
    private double InitialBalance;

    @Column(name = "remaining_balance")
    private double remainingBalance;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    @NotNull(message = "Transaction type is required")
    private TransactionType transactionType;

    @Column(name= "timestamp")
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name="status")
    @NotNull(message = "Status is required")
    private Status status;
}