package com.learnspring.ecommerce.customer;

import lombok.*;

import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Validated
public class Address {

    private String houseNumber;
    private String street;
    private String city;
    private String zipCode;
}
