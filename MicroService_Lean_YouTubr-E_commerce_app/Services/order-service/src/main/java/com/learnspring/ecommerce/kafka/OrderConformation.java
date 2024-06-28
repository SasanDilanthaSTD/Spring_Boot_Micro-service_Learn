package com.learnspring.ecommerce.kafka;

import com.learnspring.ecommerce.client.CustomerResponce;
import com.learnspring.ecommerce.dto.PurchaseResponce;
import com.learnspring.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConformation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponce customerResponce,
        List<PurchaseResponce> products
) { }
