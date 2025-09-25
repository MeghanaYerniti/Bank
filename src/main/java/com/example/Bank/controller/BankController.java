package com.example.Bank.controller;

import com.example.Bank.dto.BankAccountDTO;
import com.example.Bank.dto.TransactionDTO;
import com.example.Bank.dto.BankMapper;
import com.example.Bank.entity.BankAccountEntity;
import com.example.Bank.entity.TransactionEntity;
import com.example.Bank.service.BankAccountService;
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
    public BankAccountDTO createAccount(@Valid @RequestBody BankAccountDTO bankAccountDTO) {
        BankAccountEntity entity = BankMapper.INSTANCE.bankAccountDtoToEntity(bankAccountDTO);
        BankAccountEntity saved = bankAccountService.createAccount(entity);
        return BankMapper.INSTANCE.bankAccountEntityToDto(saved);
    }

    @GetMapping("/{id}")
    public BankAccountDTO getAccount(@PathVariable Long id) {
        BankAccountEntity entity = bankAccountService.getAccount(id);
        return BankMapper.INSTANCE.bankAccountEntityToDto(entity);
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
        return "Deleted account " + id;
    }

    @GetMapping("/allaccounts")
    public List<BankAccountDTO> getAllAccounts() {
        return bankAccountService.getAllAccounts().stream()
                .map(BankMapper.INSTANCE::bankAccountEntityToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/transfer")
    public TransactionDTO transfer(@RequestParam Long fromAccountId, @RequestParam Long toAccountId,
                                   @RequestParam double amount) {
        TransactionEntity entity = transactionService.transfer(fromAccountId, toAccountId, amount);
        return BankMapper.INSTANCE.transactionEntityToDto(entity);
    }

    @GetMapping("/{id}/transactions")
    public List<TransactionDTO> getAccountTransactions(@PathVariable Long id) {
        return transactionService.getTransactionsByAccountId(id).stream()
                .map(BankMapper.INSTANCE::transactionEntityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/report/top-balances")
    public List<BankAccountDTO> getTopBalances() {
        return bankAccountService.getTopBalances(5).stream()
                .map(BankMapper.INSTANCE::bankAccountEntityToDto)
                .collect(Collectors.toList());
    }
}