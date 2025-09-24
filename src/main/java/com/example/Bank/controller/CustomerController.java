package com.example.Bank.controller;

import com.example.Bank.entity.BankAccountEntity;
import com.example.Bank.entity.CustomerEntity;
import com.example.Bank.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequiredArgsConstructor
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/")
    public CustomerEntity createCustomer(@Valid @RequestBody CustomerEntity customerEntity) {
        return customerService.createCustomer(customerEntity);
    }

    @GetMapping("/{id}")
    public CustomerEntity getCustomer(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }

    @PutMapping("/{id}")
    public CustomerEntity updateCustomer(@PathVariable Long id, @RequestBody CustomerEntity customerEntity) {
        //CustomerEntity updatedAccount = customerService.updateCustomer(id, customerEntity);
        return customerService.updateCustomer(id, customerEntity);
    }

    @GetMapping("/")
    public List<CustomerEntity> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}
