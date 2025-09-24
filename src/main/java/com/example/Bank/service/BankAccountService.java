package com.example.Bank.service;

import com.example.Bank.entity.TransactionEntity;
import com.example.Bank.enums.AccountStatus;
import com.example.Bank.enums.AccountType;
import com.example.Bank.entity.BankAccountEntity;
import com.example.Bank.entity.CustomerEntity;
import com.example.Bank.enums.Status;
import com.example.Bank.enums.TransactionType;
import com.example.Bank.exception.HighValueTransactionException;
import com.example.Bank.exception.InsufficientFundsException;
import com.example.Bank.exception.MinimumBalanceException;
import com.example.Bank.exception.RequiresPanException;
import com.example.Bank.repository.BankAccountRepository;
import com.example.Bank.repository.CustomerRepository;
import com.example.Bank.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public BankAccountEntity createAccount(BankAccountEntity bankAccountEntity) {
        Long customerId = bankAccountEntity.getCustomer().getCustomerId();
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (bankAccountEntity.getType() == AccountType.SAVINGS) {
            if (bankAccountEntity.getInterestRate() == 0) {
                bankAccountEntity.setInterestRate(3.5);
            }
        } else if (bankAccountEntity.getType() == AccountType.CURRENT) {
            if (bankAccountEntity.getInterestRate() != 0) {
                throw new RuntimeException("Interest rate must be 0 for CURRENT accounts");
            }
        }
        bankAccountEntity.setCustomer(customer);
        bankAccountEntity.setCreatedAt(LocalDateTime.now());
        bankAccountEntity.setUpdatedAt(LocalDateTime.now());
        bankAccountEntity.setAccountStatus(AccountStatus.ACTIVE);

        return bankAccountRepository.save(bankAccountEntity);
    }

    public BankAccountEntity getAccount(Long id) {
        return bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    public double getAccountBalance(Long id) {
        BankAccountEntity account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }

    public BankAccountEntity depositAmount(Long id, double amount) {
        BankAccountEntity account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (amount > 100000) {
            CustomerEntity customer = account.getCustomer();
            if (customer.getPan() == null || customer.getPan().isBlank()) {
                throw new RequiresPanException("PAN is required for deposits exceeding 100,000");
            }
            if (amount > 200000) {
                throw new HighValueTransactionException("Deposit exceeds 200,000 limit");
            }
        }
        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setToAccountId(id);
        transaction.setAmount(amount);
        transaction.setInitialBalance(account.getBalance());
        transaction.setRemainingBalance(account.getBalance() + amount);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(Status.SUCCESS);
        transactionRepository.save(transaction);

        account.setBalance(account.getBalance() + amount);
        account.setLastTransactionTimestamp(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return bankAccountRepository.save(account);
    }

    public BankAccountEntity withdrawAmount(Long id, double amount) {
        BankAccountEntity account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }
        if (account.getType() == AccountType.SAVINGS && (account.getBalance() - amount) < 1000) {
            throw new MinimumBalanceException("SAVINGS account must maintain a minimum balance of 1000");
        }
        if (account.getType() == AccountType.CURRENT && (account.getBalance() - amount) < -5000) {
            throw new InsufficientFundsException("CURRENT account cannot go below -5000 overdraft limit");
        }

        TransactionEntity transaction = new TransactionEntity();
        transaction.setTransactionId(UUID.randomUUID());
        transaction.setFromAccountId(id);
        transaction.setAmount(amount);
        transaction.setInitialBalance(account.getBalance());
        transaction.setRemainingBalance(account.getBalance() - amount);
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(Status.SUCCESS);
        transactionRepository.save(transaction);

        account.setBalance(account.getBalance() - amount);
        account.setLastTransactionTimestamp(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return bankAccountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        BankAccountEntity account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setAccountStatus(AccountStatus.CLOSED);
        bankAccountRepository.delete(account);
    }

    public List<BankAccountEntity> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

//    public List<BankAccountEntity> getTopBalances(Integer top) {
//
//    }

//
//     allAccounts.stream().sorted((a1, a2) -> Double.compare(a2.getBalance(), a1.getBalance()))
//            .limit(topN).collect(Collectors.toList());

    public List<BankAccountEntity> searchAccountType(AccountType accountType) {
        return bankAccountRepository.findAll().stream()
                .filter(account -> account.getType() == accountType).toList();
    }

}
