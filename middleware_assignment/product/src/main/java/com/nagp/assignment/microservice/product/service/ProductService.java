package com.nagp.assignment.microservice.product.service;


import com.nagp.assignment.microservice.product.dto.OrderRequestDTO;
import com.nagp.assignment.microservice.product.dto.OrderResponseDTO;
import com.nagp.assignment.microservice.product.entity.Product;
import com.nils.gprc.order.OrderItem;
import com.nils.gprc.order.OrderRequest;
import com.nils.gprc.order.OrderResponse;
import com.nils.gprc.order.OrderServiceGrpc;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    @Getter
    @Setter
    private Product[] products;

    @GrpcClient("order")
    private OrderServiceGrpc.OrderServiceBlockingStub orderStub;

    public Optional<Product> getProductById(String id) {
        Optional<Product> matchedProduct = Arrays.stream(products).filter(product -> product.getId().equals(id)).findFirst();
        return matchedProduct;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        List<OrderItem> orderItems = orderRequestDTO.getOrderItems().stream().map(orderItemDTO -> {
           return OrderItem.newBuilder().setProductId(orderItemDTO.getProductId()).setQuantity(orderItemDTO.getQty()).build();
        }).toList();
        OrderRequest orderRequest = OrderRequest.newBuilder().setCustomerId(orderRequestDTO.getCustomerId()).addAllOrderItems(orderItems).build();
        OrderResponse orderResponse = orderStub.createOrder(orderRequest);
        log.info("response received for create order {}", orderResponse);
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(orderResponse.getOrderId());
        orderResponseDTO.setOrderAmount(orderResponse.getOrderAmount());
        orderResponseDTO.setCustomerId(orderResponse.getCustomerId());
        return orderResponseDTO;
    }

    public OrderResponseDTO updateOrder(OrderRequestDTO orderRequestDTO) {
        List<OrderItem> orderItems = orderRequestDTO.getOrderItems().stream().map(orderItemDTO -> {
            return OrderItem.newBuilder().setProductId(orderItemDTO.getProductId()).setQuantity(orderItemDTO.getQty()).build();
        }).toList();
        OrderRequest orderRequest = OrderRequest.newBuilder().setOrderId(orderRequestDTO.getOrderId()).setCustomerId(orderRequestDTO.getCustomerId()).addAllOrderItems(orderItems).build();
        OrderResponse orderResponse = orderStub.updateOrder(orderRequest);
        log.info("response received for update order {}", orderResponse);
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(orderResponse.getOrderId());
        orderResponseDTO.setOrderAmount(orderResponse.getOrderAmount());
        orderResponseDTO.setCustomerId(orderResponse.getCustomerId());
        return orderResponseDTO;
    }
}
