package com.example.Bank.service;

import com.example.Bank.repository.BankAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// @AllArgsConstructor
@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;



}
