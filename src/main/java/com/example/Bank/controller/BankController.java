package com.example.Bank.controller;

import com.example.Bank.entity.BankAccountEntity;
import com.example.Bank.entity.CustomerEntity;
import com.example.Bank.repository.BankAccountRepository;
import com.example.Bank.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class BankController {
    private BankAccountService bankAccountService;

    @PostMapping("/")
    public String createAccount(@RequestBody BankAccountEntity bankAccountEntity) {
        bankAccountService.createAccount(bankAccountEntity);
        return bankAccountEntity.toString();
    }

    @GetMapping("/{id}")
    public BankAccountEntity getAccount(@PathVariable Long id) {
        return bankAccountService.getAccount(id);
    }

    @GetMapping("/{id}/balance")
    public double getBalance(@PathVariable Long id) {
        return bankAccountService.getAccountBalance(id);
    }

    @PutMapping("/{id}/deposit")
    public String deposit(@PathVariable Long id, @RequestParam double amount) {
        BankAccountEntity updatedAccount = bankAccountService.depositAmount(id, amount);
        return "Deposited " + amount + ". New Balance: " + updatedAccount.getBalance();
    }

    @PutMapping("/{id}/withdraw")
    public String withdraw(@PathVariable Long id, @RequestParam double amount) {
        BankAccountEntity updatedAccount = bankAccountService.withdrawAmount(id, amount);
        return "Deposited " + amount + ". New Balance: " + updatedAccount.getBalance();
    }

    @DeleteMapping("/{id}")
    public String deleteAccount(@PathVariable Long id) {
        bankAccountService.deleteAccount(id);
        return  "Deleted account " + id;
    }

    @GetMapping("/allaccounts")
    public List<BankAccountEntity> getAllAccounts() {
        return  bankAccountService.getAllAccounts();
    }

}
