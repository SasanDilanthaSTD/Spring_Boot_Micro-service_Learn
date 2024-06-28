package com.learnspring.ecommerce.dto;

import java.math.BigDecimal;

public record PurchaseResponce(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        double quantity
) { }
