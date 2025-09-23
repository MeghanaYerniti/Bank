package com.example.Bank.controller;

import com.example.Bank.entity.CustomerEntity;
import com.example.Bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/v1/customers")
    public String createCustomer(@RequestBody CustomerEntity customerEntity) {
        customerService.createCustomer(customerEntity);
        return customerEntity.toString();
    }

}
