package com.learnspring.ecommerce.client;

import com.learnspring.ecommerce.dto.PurchaseRequest;
import com.learnspring.ecommerce.dto.PurchaseResponce;
import com.learnspring.ecommerce.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@RequiredArgsConstructor
public class ProductClient {
    @Value("${application.config.product-url}")
    private String productUrl;
    private final RestTemplate restTemplate;

    public List<PurchaseResponce> purchaseProducts(List<PurchaseRequest> requestBody){
        HttpHeaders headers = new HttpHeaders();
        headers.set(CONTENT_TYPE, APPLICATION_JSON_VALUE);

        HttpEntity<List<PurchaseRequest>> requestEntity = new HttpEntity<>(requestBody, headers);
        ParameterizedTypeReference<List<PurchaseResponce>> responseType =
                new ParameterizedTypeReference<>() {};
        ResponseEntity<List<PurchaseResponce>> responseEntity = restTemplate.exchange(
                productUrl + "/purchase",
                POST,
                requestEntity,
                responseType
        );

        if(responseEntity.getStatusCode().isError()){
            throw new BusinessException("An error occurred while processing purchase request: " + responseEntity.getBody());
        }

        return responseEntity.getBody();
    }
}
