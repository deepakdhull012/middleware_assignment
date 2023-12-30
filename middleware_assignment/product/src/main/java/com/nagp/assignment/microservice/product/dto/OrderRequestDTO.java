package com.nagp.assignment.microservice.product.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private String orderId;
    private String customerId;
    private List<OrderItemDTO> orderItems;


}
