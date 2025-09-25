package com.example.Bank.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CustomerDTO {

    private Long customerId;

    @NotBlank(message = "Name is required")
    @Size(min = 5, max = 100, message = "Name must be between 5 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must not contain special characters")
    private String name;

    @NotBlank(message = "PAN is required")
    @Size(min = 8, message = "PAN must be at least 8 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "PAN must not contain special characters")
    private String pan;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required")
//    @Pattern(regexp = "^(\\+91)?[6-9]\\d{9}$", message = "Invalid Indian phone number")
    @Pattern(regexp = "^(?!(\\d)\\1{9})(\\+91)?[6-9]\\d{9}$", message = "Invalid Indian phone number")
    private String phone;

    private List<BankAccountDTO> accounts;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}