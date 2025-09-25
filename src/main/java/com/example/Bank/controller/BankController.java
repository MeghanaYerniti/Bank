package com.example.Bank.controller;

import com.example.Bank.entity.BankAccountEntity;
import com.example.Bank.entity.CustomerEntity;
import com.example.Bank.entity.TransactionEntity;
import com.example.Bank.repository.BankAccountRepository;
import com.example.Bank.service.BankAccountService;
import com.example.Bank.service.CustomerService;
import com.example.Bank.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class BankController {
    private final BankAccountService bankAccountService;
    private final TransactionService transactionService;

    @PostMapping("/")
    public BankAccountEntity createAccount(@Valid @RequestBody BankAccountEntity bankAccountEntity) {
        return bankAccountService.createAccount(bankAccountEntity);
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
        return "Withdraw " + amount + " New Balance: " + updatedAccount.getBalance();
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

    @PutMapping("/transfer")
    public TransactionEntity transfer(@RequestParam Long fromAccountId, @RequestParam Long toAccountId,
                                      @RequestParam double amount) {
        return transactionService.transfer(fromAccountId, toAccountId, amount);
    }

    @GetMapping("/{id}/transactions")
    public List<TransactionEntity> getAccountTransactions(@PathVariable Long id) {
        return transactionService.getTransactionsByAccountId(id);
    }

    @GetMapping("/report/top-balances")
    public List<BankAccountEntity> getTopBalances() {
        return bankAccountService.getTopBalances(5);
    }

}
