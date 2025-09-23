package com.example.Bank.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
    private Long customerId;

    @Column(name="name")
    private String name;

    @Column(name="pan")
    private String pan;

    @Column(name="email")
    private String email;

    @Column(name="phone")
    private String phone;

    @ToString.Exclude
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankAccountEntity> accounts = new ArrayList<>();

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="update_at")
    private LocalDateTime updatedAt;

}