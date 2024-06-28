package com.learnspring.ecommerce.dto;

import com.learnspring.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) { }
