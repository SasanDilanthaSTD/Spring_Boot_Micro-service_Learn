package com.learnspring.ecommerce.controllers;

import com.learnspring.ecommerce.dto.ProductPurchaseRequest;
import com.learnspring.ecommerce.dto.ProductPurchaseResponce;
import com.learnspring.ecommerce.dto.ProductRequest;
import com.learnspring.ecommerce.dto.ProductResponce;
import com.learnspring.ecommerce.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<Integer> createProduct(@RequestBody @Valid ProductRequest request){
        return ResponseEntity.ok(service.createProduct(request));
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponce>> purchaseProducts(
            @RequestBody List<ProductPurchaseRequest> request
    ){
        return ResponseEntity.ok(service.purchaseProducts(request));
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponce> findById(@PathVariable("product-id") Integer productId){
        return  ResponseEntity.ok(service.findById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponce>> getAllProducts(){
        return ResponseEntity.ok(service.getAllProduct());
    }
}
