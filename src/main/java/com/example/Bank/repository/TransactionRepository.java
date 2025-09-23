package com.example.Bank.repository;

import com.example.Bank.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, UUID> {

}
