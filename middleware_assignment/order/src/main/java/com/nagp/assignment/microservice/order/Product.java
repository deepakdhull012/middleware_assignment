package com.nagp.assignment.microservice.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String id;
    private String name;
    private String description;
    private Double price;
    private Integer stocksQuantity;
    private String[] colorsAvailable;
}
