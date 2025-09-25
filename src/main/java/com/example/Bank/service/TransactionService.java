package com.example.Bank.service;

import com.example.Bank.entity.BankAccountEntity;
import com.example.Bank.entity.CustomerEntity;
import com.example.Bank.entity.TransactionEntity;
import com.example.Bank.enums.AccountType;
import com.example.Bank.enums.Status;
import com.example.Bank.enums.TransactionType;
import com.example.Bank.exception.HighValueTransactionException;
import com.example.Bank.exception.InsufficientFundsException;
import com.example.Bank.exception.MinimumBalanceException;
import com.example.Bank.exception.RequiresPanException;
import com.example.Bank.repository.BankAccountRepository;
import com.example.Bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;

    public TransactionEntity transfer(Long fromAccountId, Long toAccountId, double amount) {
        BankAccountEntity fromAccount = bankAccountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("From account not found"));
        BankAccountEntity toAccount = bankAccountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("To account not found"));

        if (fromAccount.getBalance() < amount) {
            TransactionEntity failedTransaction = createFailedTransaction(fromAccountId, toAccountId, amount);
            transactionRepository.save(failedTransaction);
            throw new InsufficientFundsException("Insufficient funds for transfer");
        }
        if (fromAccount.getType() == AccountType.SAVINGS && (fromAccount.getBalance() - amount) < 1000) {
            TransactionEntity failedTransaction = createFailedTransaction(fromAccountId, toAccountId, amount);
            transactionRepository.save(failedTransaction);
            throw new MinimumBalanceException("SAVINGS account must maintain a minimum balance of 1000");
        }
        // High-value transfer checks
        if (amount > 100000) {
            CustomerEntity customer = fromAccount.getCustomer();
            if (customer.getPan() == null || customer.getPan().isBlank()) {
                TransactionEntity failedTransaction = createFailedTransaction(fromAccountId, toAccountId, amount);
                transactionRepository.save(failedTransaction);
                throw new RequiresPanException("PAN is required for transfers exceeding 100,000");
            }
            if (amount > 200000) {
                TransactionEntity failedTransaction = createFailedTransaction(fromAccountId, toAccountId, amount);
                transactionRepository.save(failedTransaction);
                throw new HighValueTransactionException("Transfer exceeds 200,000 limit");
            }
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        fromAccount.setLastTransactionTimestamp(LocalDateTime.now());
        toAccount.setLastTransactionTimestamp(LocalDateTime.now());
        fromAccount.setUpdatedAt(LocalDateTime.now());
        toAccount.setUpdatedAt(LocalDateTime.now());
        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setFromAccountId(fromAccountId);
        transaction.setToAccountId(toAccountId);
        transaction.setAmount(amount);
        transaction.setInitialBalance(fromAccount.getBalance() + amount);
        transaction.setRemainingBalance(fromAccount.getBalance());
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(Status.SUCCESS);
        return transactionRepository.save(transaction);
    }

    private TransactionEntity createFailedTransaction(Long fromAccountId, Long toAccountId, double amount) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setFromAccountId(fromAccountId);
        transaction.setToAccountId(toAccountId);
        transaction.setAmount(amount);
        transaction.setInitialBalance(bankAccountRepository.findById(fromAccountId)
                .map(BankAccountEntity::getBalance).orElse(0.0));
        transaction.setRemainingBalance(transaction.getInitialBalance());
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(Status.FAILED);
        return transaction;
    }

    public List<TransactionEntity> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public TransactionEntity getTransaction(UUID id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public List<TransactionEntity> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findAll().stream()
                .filter(t -> (t.getFromAccountId() != null && t.getFromAccountId().equals(accountId)) ||
                        (t.getToAccountId() != null && t.getToAccountId().equals(accountId)))
                .collect(Collectors.toList());
    }
}
