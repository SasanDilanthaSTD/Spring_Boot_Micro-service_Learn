package com.learnspring.ecommerce.services;

import com.learnspring.ecommerce.dto.ProductPurchaseRequest;
import com.learnspring.ecommerce.dto.ProductPurchaseResponce;
import com.learnspring.ecommerce.dto.ProductRequest;
import com.learnspring.ecommerce.dto.ProductResponce;
import com.learnspring.ecommerce.exception.ProductPurchaseException;
import com.learnspring.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public Integer createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    public List<ProductPurchaseResponce> purchaseProducts(List<ProductPurchaseRequest> request) {
        // user want product ids
        var productsIds = request.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        // data base have product ids
        var storedProducts = repository.findAllByIdInOrderById(productsIds);
        if (productsIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products not exist in the store");
        }

        var storedRequests = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponce>();
        for(int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = storedRequests.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()){
                throw new ProductPurchaseException("Insufficient quantity product with ID::" + productRequest.productId());
            }
            // update the new available quantity
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            // fill purchasedProduct
            purchasedProducts.add(mapper.toProductPurchaseResponce(product, productRequest.quantity()));
        }

        return purchasedProducts;
    }

    public ProductResponce findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::fromProductResponce)
                .orElseThrow(
                        () -> new EntityNotFoundException("Product not found with the ID:: " + productId)
                );
    }

    public List<ProductResponce> getAllProduct() {
        return repository.findAll()
                .stream()
                .map(mapper::fromProductResponce)
                .collect(Collectors.toList());
    }
}
