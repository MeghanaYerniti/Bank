package com.example.Bank.service;

import com.example.Bank.enums.Accountstatus;
import com.example.Bank.enums.Accounttype;
import com.example.Bank.entity.BankAccountEntity;
import com.example.Bank.entity.CustomerEntity;
import com.example.Bank.repository.BankAccountRepository;
import com.example.Bank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    private final CustomerRepository customerRepository;



    public BankAccountEntity createAccount(BankAccountEntity bankAccountEntity) {

        if(bankAccountEntity.getCustomer().getCustomerId() == null){
            throw new RuntimeException("Customer ID is required to create an account");
        }
        if (bankAccountEntity.getType() == Accounttype.SAVINGS) {
            if (bankAccountEntity.getInterestRate() <= 0) {
                throw new RuntimeException("Interest rate must be provided for SAVINGS accounts");
            }
        } else {
            bankAccountEntity.setInterestRate(0);
        }
        Long customerId = bankAccountEntity.getCustomer().getCustomerId();
        CustomerEntity customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        bankAccountEntity.setCustomer(customer);
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
        account.setBalance(account.getBalance() + amount);
        account.setLastTransactionTimestamp(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return bankAccountRepository.save(account);
    }

    public BankAccountEntity withdrawAmount(Long id, double amount) {
        BankAccountEntity account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient funds to deposit amount");
        }
        account.setBalance(account.getBalance() - amount);
        account.setLastTransactionTimestamp(LocalDateTime.now());
        account.setUpdatedAt(LocalDateTime.now());
        return bankAccountRepository.save(account);
    }

    public void deleteAccount(Long id) {
        BankAccountEntity account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setAccountStatus(Accountstatus.CLOSE);
        bankAccountRepository.delete(account);
    }

    public List<BankAccountEntity> getAllAccounts() {
        return bankAccountRepository.findAll();
    }

}
