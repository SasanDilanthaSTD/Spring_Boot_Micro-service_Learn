package com.learnspring.ecommerce.repository;

import com.learnspring.ecommerce.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
