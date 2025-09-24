package com.example.Bank.controller;

import com.example.Bank.entity.BankAccountEntity;
import com.example.Bank.enums.AccountType;
import com.example.Bank.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/accounts")
@RequiredArgsConstructor
public class BankAccountController2 {

    private final BankAccountService bankAccountService;

    @GetMapping("/search")
    public List<BankAccountEntity> searchAccountType(@RequestParam AccountType accountType) {
        return bankAccountService.searchAccountType(accountType);
    }

    @GetMapping("{id}/intrest")
    public double getInterest(@PathVariable Long id) {
        BankAccountEntity account = bankAccountService.getAccount(id);
        if (account.getType() != AccountType.SAVINGS) {
            throw new RuntimeException("Interest calculation is only for SAVINGS accounts");
        }
        return account.getBalance() * (account.getInterestRate() / 100);
    }

}
