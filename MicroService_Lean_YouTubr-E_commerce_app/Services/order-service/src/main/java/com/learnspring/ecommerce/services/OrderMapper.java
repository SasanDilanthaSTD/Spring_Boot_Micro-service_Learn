package com.learnspring.ecommerce.services;

import com.learnspring.ecommerce.dto.OrderRequest;
import com.learnspring.ecommerce.dto.OrderResponse;
import com.learnspring.ecommerce.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(OrderRequest request) {
        return Order.builder()
                .Id(request.id())
                .reference(request.reference())
                .customerId(request.customerId())
                .totalAmount(request.amount())
                .paymentMethod(request.paymentMethod())
                .build();
    }

    public OrderResponse fromOder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
