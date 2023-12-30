package com.nagp.assignment.microservice.product.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagp.assignment.microservice.product.entity.Product;
import com.nagp.assignment.microservice.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class ProductsLoader {

    @Autowired
    private ProductService productService;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ClassPathResource resource = new ClassPathResource("products.json");
            Product[] products = objectMapper.readValue(
                    resource.getFile(), Product[].class);
            log.info(Arrays.toString(products));
            productService.setProducts(products);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
