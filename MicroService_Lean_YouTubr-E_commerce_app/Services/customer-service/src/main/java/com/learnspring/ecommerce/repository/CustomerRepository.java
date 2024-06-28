package com.learnspring.ecommerce.repository;

import com.learnspring.ecommerce.customer.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> { }
