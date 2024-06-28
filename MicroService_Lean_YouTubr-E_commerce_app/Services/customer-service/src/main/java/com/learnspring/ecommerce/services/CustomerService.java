package com.learnspring.ecommerce.services;

import com.ctc.wstx.util.StringUtil;
import com.learnspring.ecommerce.customer.Customer;
import com.learnspring.ecommerce.dto.CustomerRequest;
import com.learnspring.ecommerce.dto.CustomerResponce;
import com.learnspring.ecommerce.exceptions.CustomerNotFoundException;
import com.learnspring.ecommerce.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public String createCustomer(CustomerRequest request) {
        var customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    public void upadateCustomer(CustomerRequest request) {
        var customer = repository.findById(request.id())
                .orElseThrow(
                        ()-> new CustomerNotFoundException(
                                format("Can't update Customer:: No customer found with the provide ID:: %s", request.id())
                        )
                );
        mergeCustomer(customer,request);
        repository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName())){
            customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())){
            customer.setLastName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())){
            customer.setEmail(request.email());
        }
        if (request.address() != null){
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponce> findAllCustomers() {
        return repository.findAll()
                .stream()
                .map(mapper::fromCustomer)
                .collect(Collectors.toList());
    }

    public Boolean exitsById(String customerId) {
        return repository.findById(customerId)
                .isPresent();
    }

    public CustomerResponce findById(String customerId) {
        return repository.findById(customerId)
                .map(mapper::fromCustomer)
                .orElseThrow(
                        () -> new CustomerNotFoundException(
                                format("Can't find customer with ID:: %s", customerId)
                        )
                );
    }

    public void deleteCustomer(String customerId) {
        repository.deleteById(customerId);
    }
}
