package com.learnspring.ecommerce.services;

import com.learnspring.ecommerce.client.CustomerClient;
import com.learnspring.ecommerce.client.PaymentClient;
import com.learnspring.ecommerce.client.PaymentRequest;
import com.learnspring.ecommerce.client.ProductClient;
import com.learnspring.ecommerce.dto.OrderLineRequest;
import com.learnspring.ecommerce.dto.OrderRequest;
import com.learnspring.ecommerce.dto.OrderResponse;
import com.learnspring.ecommerce.dto.PurchaseRequest;
import com.learnspring.ecommerce.exceptions.BusinessException;
import com.learnspring.ecommerce.kafka.OrderConformation;
import com.learnspring.ecommerce.kafka.OrderProducer;
import com.learnspring.ecommerce.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest request) {
        /**
         *      Step of the createOder method logic
         *      ===================================
         *      - check the customer ---> customer-service (openFeign)
         *      - purchase the product ---> product-service (RestTemplate)
         *      - persist order
         *      - persist oder line
         *      - start payment process
         *      - send the order conformation ---> notification-service (kafka)
         */

        // check the customer and get the customer details
        var customer = this.customerClient.findCustomerByID(request.customerId())
                .orElseThrow(
                        () -> new BusinessException("Cant not create the order:: No customer exist provide ID")
                );

        // get purchase product details
        var purchaseProducts = this.productClient.purchaseProducts(request.products());
        // persist order
        var order = this.repository.save(mapper.toOrder(request));
        // persist order line
        for(PurchaseRequest purchaseRequest : request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        // to start payment process
        var requestPayment = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(requestPayment);

        // send the order comformation
        orderProducer.sendOderConformation(
                new OrderConformation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchaseProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> getAllOrders() {
        return  repository.findAll()
                .stream()
                .map(mapper::fromOder)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOder)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Order with ID %d not found", orderId))
                );
    }
}
