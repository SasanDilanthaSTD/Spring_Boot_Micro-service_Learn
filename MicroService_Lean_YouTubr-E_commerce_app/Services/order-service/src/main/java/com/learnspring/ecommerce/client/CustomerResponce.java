package com.learnspring.ecommerce.client;

public record CustomerResponce(
        String id,
        String firstname,
        String lastname,
        String email
) { }
