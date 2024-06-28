package com.learnspring.ecommerce.dto;

import com.learnspring.ecommerce.customer.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        String id,
        @NotNull(message = "Customer firstname is required")
        String firstName,
        @NotNull(message = "Customer lastname is required")
        String lastName,
        @NotNull(message = "Customer mail is required")
        @Email(message = "Customer mail is not valied")
        String email,
        Address address
) {}
