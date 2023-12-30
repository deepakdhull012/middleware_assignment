package com.nagp.assignment.microservice.product.dto;

import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private String customerId;
    private String orderId;
    private float orderAmount;
}
