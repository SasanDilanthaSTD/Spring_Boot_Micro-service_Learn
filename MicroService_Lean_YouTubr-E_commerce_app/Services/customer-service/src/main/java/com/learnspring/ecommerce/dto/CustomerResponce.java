package com.learnspring.ecommerce.dto;

import com.learnspring.ecommerce.customer.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerResponce(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) { }
