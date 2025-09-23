package com.example.Bank.controller;

import com.example.Bank.entity.CustomerEntity;
import com.example.Bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/")
    public String createCustomer(@RequestBody CustomerEntity customerEntity) {
        customerService.createCustomer(customerEntity);
        return customerEntity.toString();
    }

    @GetMapping("/{id}")
    public CustomerEntity getCustomer(@PathVariable Long id) {
        return customerService.getCustomer(id);
    }

//
//    @GetMapping("{id}/balance")
//    public double getBalance(@PathVariable Long id) {
//        return customerService.getBalance(id);
//    }

}
