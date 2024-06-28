package com.learnspring.ecommerce.repository;

import com.learnspring.ecommerce.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order, Integer> {
}
