package com.learnspring.ecommerce.controllers;

import com.learnspring.ecommerce.dto.PaymentRequest;
import com.learnspring.ecommerce.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService service;

    @PostMapping
    public ResponseEntity<Integer> createPayment(
            @RequestBody @Valid PaymentRequest request
    ) {
        return ResponseEntity.ok(this.service.createPayment(request));
    }

    @GetMapping
    public ResponseEntity<String> testIsWorking(){
        return ResponseEntity.ok("api link is working");
    }
}
