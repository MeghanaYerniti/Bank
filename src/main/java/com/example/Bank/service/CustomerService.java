package com.example.Bank.service;

import com.example.Bank.entity.CustomerEntity;
import com.example.Bank.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerEntity createCustomer(CustomerEntity customerEntity) {
        if (customerEntity.getAccounts() != null) {
            customerEntity.getAccounts().forEach(account -> account.setCustomer(customerEntity));
        }
        return customerRepository.save(customerEntity);
    }

    public CustomerEntity getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

}
