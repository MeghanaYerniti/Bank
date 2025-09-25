package com.example.Bank.controller;

import com.example.Bank.dto.BankAccountDTO;
import com.example.Bank.enums.AccountType;
import com.example.Bank.dto.BankMapper;
import com.example.Bank.entity.BankAccountEntity;
import com.example.Bank.service.BankAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/accounts")
@RequiredArgsConstructor
public class BankAccountController2 {

    private final BankAccountService bankAccountService;

    @GetMapping("/search")
    public List<BankAccountDTO> searchAccountType(@RequestParam AccountType accountType) {
        return bankAccountService.searchAccountType(accountType).stream()
                .map(BankMapper.INSTANCE::bankAccountEntityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("{id}/interest")
    public double getInterest(@PathVariable Long id) {
        BankAccountEntity account = bankAccountService.getAccount(id);
        if (account.getType() != AccountType.SAVINGS) {
            throw new RuntimeException("Interest calculation is only for SAVINGS accounts");
        }
        return account.getBalance() * (account.getInterestRate() / 100);
    }

    @PostMapping("/")
    public BankAccountDTO createAccountV2(@Valid @RequestBody BankAccountDTO bankAccountDTO) {
        BankAccountEntity entity = BankMapper.INSTANCE.bankAccountDtoToEntity(bankAccountDTO);
        BankAccountEntity saved = bankAccountService.createAccountV2(entity);
        return BankMapper.INSTANCE.bankAccountEntityToDto(saved);
    }
}