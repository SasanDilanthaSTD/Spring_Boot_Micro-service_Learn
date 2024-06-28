package com.learnspring.ecommerce.services;

import com.learnspring.ecommerce.dto.PaymentRequest;
import com.learnspring.ecommerce.payment.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentRequest request) {
        return Payment.builder()
                .id(request.id())
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .orderId(request.orderId())
                .build();
    }
}
