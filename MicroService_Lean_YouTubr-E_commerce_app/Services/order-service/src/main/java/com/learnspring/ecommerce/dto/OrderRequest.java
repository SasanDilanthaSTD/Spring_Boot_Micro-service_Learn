package com.learnspring.ecommerce.dto;

import com.learnspring.ecommerce.order.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Integer id,
        String reference,
        @Positive(message = "Amount should be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method should be precised")
        PaymentMethod paymentMethod,
        @NotNull(message = "Customer id should be precised")
        @NotBlank(message = "Customer id should be precised")
        @NotEmpty(message = "Customer id should be precised")
        String customerId,
        @NotEmpty(message = "Should purchase at least one product")
        List<PurchaseRequest> products
) { }
