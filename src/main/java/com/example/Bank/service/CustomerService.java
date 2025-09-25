package com.example.Bank.service;

import com.example.Bank.entity.CustomerEntity;
import com.example.Bank.enums.AccountStatus;
import com.example.Bank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

//    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
//        if (customerEntity.getAccounts() != null) {
//            customerEntity.getAccounts().forEach(account -> account.setCustomer(customerEntity));
//        }
//        return customerRepository.save(customerEntity);
//    }

    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
        if (customerEntity.getAccounts() != null) {
            customerEntity.getAccounts().forEach(account -> {
                account.setCustomer(customerEntity);
                if (account.getCreatedAt() == null) account.setCreatedAt(LocalDateTime.now());
                if (account.getUpdatedAt() == null) account.setUpdatedAt(LocalDateTime.now());
                if (account.getAccountStatus() == null) account.setAccountStatus(AccountStatus.ACTIVE);
            });
        }
        if (customerEntity.getCreatedAt() == null) customerEntity.setCreatedAt(LocalDateTime.now());
        if (customerEntity.getUpdatedAt() == null) customerEntity.setUpdatedAt(LocalDateTime.now());
        CustomerEntity savedCustomer = customerRepository.save(customerEntity); // Save customer first
        if (customerEntity.getAccounts() != null) {
            customerEntity.getAccounts().forEach(account -> {
                if (account.getAccountId() < 10000000) {
                    throw new RuntimeException("Account ID must be at least 8 digits");
                }
                account.setCustomer(savedCustomer);
            });
        }
        return savedCustomer;
    }
    public CustomerEntity getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public CustomerEntity updateCustomer(Long id, CustomerEntity updatedCustomer) {
        CustomerEntity existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        if (updatedCustomer.getName() != null && !updatedCustomer.getName().isBlank()) {
            existingCustomer.setName(updatedCustomer.getName());
        }
        if (updatedCustomer.getPan() != null && !updatedCustomer.getPan().isBlank()) {
            existingCustomer.setPan(updatedCustomer.getPan());
        }
        if (updatedCustomer.getEmail() != null && !updatedCustomer.getEmail().isBlank()) {
            existingCustomer.setEmail(updatedCustomer.getEmail());
        }
        if (updatedCustomer.getPhone() != null && !updatedCustomer.getPhone().isBlank()) {
            existingCustomer.setPhone(updatedCustomer.getPhone());
        }

        //existingCustomer.setUpdatedAt(LocalDateTime.now());
        return customerRepository.save(existingCustomer);
    }

    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }
}
