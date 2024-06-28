package com.learnspring.ecommerce.controllers;

import com.learnspring.ecommerce.dto.CustomerRequest;
import com.learnspring.ecommerce.dto.CustomerResponce;
import com.learnspring.ecommerce.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request) {
        return ResponseEntity.ok(service.createCustomer(request));
    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest request) {
        service.upadateCustomer(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponce>> getAllCustomers() {
        return ResponseEntity.ok(service.findAllCustomers());
    }

    @GetMapping("/exits/{customer-id}")
    public ResponseEntity<Boolean> exitsCustomerById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok(service.exitsById(customerId));
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponce> findCustomerById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok(service.findById(customerId));
    }

    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable("customer-id") String customerId) {
        service.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }
}
