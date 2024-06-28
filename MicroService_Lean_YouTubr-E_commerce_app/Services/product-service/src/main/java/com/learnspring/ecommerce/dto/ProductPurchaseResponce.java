package com.learnspring.ecommerce.dto;

import java.math.BigDecimal;

public record ProductPurchaseResponce(
        Integer productId,
        String name,
        String description,
        double quantity,
        BigDecimal price
) { }
