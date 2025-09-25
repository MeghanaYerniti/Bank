package com.example.Bank.controller;

import com.example.Bank.dto.CustomerDTO;
import com.example.Bank.dto.BankMapper;
import com.example.Bank.entity.CustomerEntity;
import com.example.Bank.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/")
    public CustomerDTO createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerEntity entity = BankMapper.INSTANCE.customerDtoToEntity(customerDTO);
        CustomerEntity saved = customerService.createCustomer(entity);
        return BankMapper.INSTANCE.customerEntityToDto(saved);
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomer(@PathVariable Long id) {
        CustomerEntity entity = customerService.getCustomer(id);
        return BankMapper.INSTANCE.customerEntityToDto(entity);
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerEntity entity = BankMapper.INSTANCE.customerDtoToEntity(customerDTO);
        entity.setCustomerId(id);
        CustomerEntity updated = customerService.updateCustomer(id, entity);
        return BankMapper.INSTANCE.customerEntityToDto(updated);
    }

    @GetMapping("/")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers().stream()
                .map(BankMapper.INSTANCE::customerEntityToDto)
                .collect(Collectors.toList());
    }
}