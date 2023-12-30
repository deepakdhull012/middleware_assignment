package com.nagp.assignment.microservice.order.service;


import com.nagp.assignment.microservice.order.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
public class DataProviderService {
    @Getter
    @Setter
    private Product[] products;

    public Optional<Product> getProductById(String id) {
        log.info("searching for id {}", id);
        Optional<Product> matchedProduct = Arrays.stream(products).filter(product -> product.getId().equals(id)).findFirst();
        log.info("matchedProduct {}", matchedProduct);
        return matchedProduct;
    }
}
