package com.example.Bank.controller;

import com.example.Bank.entity.TransactionEntity;
import com.example.Bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/")
    public List<TransactionEntity> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public TransactionEntity getTransaction(@PathVariable UUID id) {
        return transactionService.getTransaction(id);
    }

    @PutMapping("/transfer")
    public TransactionEntity transfer(@RequestParam Long fromAccountId, @RequestParam Long toAccountId,
                                      @RequestParam double amount) {
        return transactionService.transfer(fromAccountId, toAccountId, amount);
    }

    @GetMapping("/{id}/transactions")
    public List<TransactionEntity> getAccountTransactions(@PathVariable Long id) {
        return transactionService.getTransactionsByAccountId(id);
    }
}