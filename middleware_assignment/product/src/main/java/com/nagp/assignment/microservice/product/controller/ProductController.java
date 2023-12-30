package com.nagp.assignment.microservice.product.controller;


import com.nagp.assignment.microservice.product.dto.OrderRequestDTO;
import com.nagp.assignment.microservice.product.dto.OrderResponseDTO;
import com.nagp.assignment.microservice.product.entity.Product;
import com.nagp.assignment.microservice.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public Optional<Product> findProductById(@PathVariable("id") String id) {
        return productService.getProductById(id);
    }

    @GetMapping("/")
    public ResponseEntity<Product[]> getAllProducts() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productService.getProducts());
    }

    @PostMapping("/create-order")
    public OrderResponseDTO placeOrder(@RequestBody() OrderRequestDTO orderRequestDTO) {
        return productService.createOrder(orderRequestDTO);
    }

    @PostMapping("/update-order")
    public OrderResponseDTO updateOrder(@RequestBody() OrderRequestDTO orderRequestDTO) {
        return productService.updateOrder(orderRequestDTO);
    }

}
