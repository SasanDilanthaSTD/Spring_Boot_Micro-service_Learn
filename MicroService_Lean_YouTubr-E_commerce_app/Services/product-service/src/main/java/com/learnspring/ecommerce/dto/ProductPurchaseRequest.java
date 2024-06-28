package com.learnspring.ecommerce.dto;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "Product ID is mandatory")
        Integer productId,
        @NotNull(message = "Quantity is mandatory")
        double quantity
) { }
