package com.learnspring.ecommerce.services;

import com.learnspring.ecommerce.dto.ProductPurchaseResponce;
import com.learnspring.ecommerce.dto.ProductRequest;
import com.learnspring.ecommerce.dto.ProductResponce;
import com.learnspring.ecommerce.product.Category;
import com.learnspring.ecommerce.product.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductRequest request) {
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .availableQuantity(request.availableQuantity())
                .category(
                        Category.builder()
                                .id(request.categoryId())
                                .build()
                )
                .build();
    }

    public ProductResponce fromProductResponce(Product product) {
        return new ProductResponce(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }

    public ProductPurchaseResponce toProductPurchaseResponce(Product product, double quantity) {
        return new ProductPurchaseResponce(
                product.getId(),
                product.getName(),
                product.getDescription(),
                quantity,
                product.getPrice()
        );
    }
}
