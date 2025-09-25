package com.example.Bank.controller;

import com.example.Bank.dto.TransactionDTO;
import com.example.Bank.dto.BankMapper;
import com.example.Bank.entity.TransactionEntity;
import com.example.Bank.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/")
    public List<TransactionDTO> getAllTransactions() {
        return transactionService.getAllTransactions().stream()
                .map(BankMapper.INSTANCE::transactionEntityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TransactionDTO getTransaction(@PathVariable UUID id) {
        TransactionEntity entity = transactionService.getTransaction(id);
        return BankMapper.INSTANCE.transactionEntityToDto(entity);
    }
}