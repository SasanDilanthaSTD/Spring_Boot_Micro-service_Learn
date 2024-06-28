package com.learnspring.ecommerce.services;

import com.learnspring.ecommerce.customer.Customer;
import com.learnspring.ecommerce.dto.CustomerRequest;
import com.learnspring.ecommerce.dto.CustomerResponce;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {

    public Customer toCustomer(CustomerRequest request) {
        if (request == null) {
            return null;
        }
        return Customer.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .address(request.address())
                .build();
    }

    public CustomerResponce fromCustomer(Customer customer) {
        return new CustomerResponce(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
