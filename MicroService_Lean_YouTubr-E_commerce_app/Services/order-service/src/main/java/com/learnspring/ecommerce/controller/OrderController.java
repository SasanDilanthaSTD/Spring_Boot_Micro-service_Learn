package com.learnspring.ecommerce.controller;

import com.learnspring.ecommerce.dto.OrderRequest;
import com.learnspring.ecommerce.dto.OrderResponse;
import com.learnspring.ecommerce.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Integer orderId) {
        return  ResponseEntity.ok(orderService.getOrderById(orderId));
    }
}
