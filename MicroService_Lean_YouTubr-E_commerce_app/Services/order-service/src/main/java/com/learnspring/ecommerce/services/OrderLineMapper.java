package com.learnspring.ecommerce.services;

import com.learnspring.ecommerce.dto.OrderLineRequest;
import com.learnspring.ecommerce.dto.OrderLineResponce;
import com.learnspring.ecommerce.dto.OrderResponse;
import com.learnspring.ecommerce.order.Order;
import com.learnspring.ecommerce.order.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .Id(orderLineRequest.id())
                .quantity(orderLineRequest.quantity())
                .order(
                        Order.builder()
                                .Id(orderLineRequest.orderId())
                                .build()
                )
                .productId(orderLineRequest.productId())
                .build();
    }

    public OrderLineResponce toOrderLineResponce(OrderLine orderLine) {
        return new OrderLineResponce(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
