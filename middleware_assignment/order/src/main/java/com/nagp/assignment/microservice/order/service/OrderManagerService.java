package com.nagp.assignment.microservice.order.service;


import com.nagp.assignment.microservice.order.Product;
import com.nagp.assignment.microservice.order.producer.RabbitMQProducer;
import com.nils.gprc.order.OrderItem;
import com.nils.gprc.order.OrderRequest;
import com.nils.gprc.order.OrderResponse;
import com.nils.gprc.order.OrderServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@GrpcService
public class OrderManagerService extends OrderServiceGrpc.OrderServiceImplBase {

    @Autowired
    private DataProviderService dataProviderService;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @Override
    public void createOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        log.info("Request received from client on grpc server for create order");
        String orderId = UUID.randomUUID().toString();
        int orderAmount = (int)getOrderAmount(request.getOrderItemsList());
        OrderResponse response = OrderResponse.newBuilder()
                .setOrderId(orderId)
                .setOrderAmount(orderAmount)
                .setCustomerId(request.getCustomerId())
                .build();
        log.info("OrderAmount is {}", orderAmount);
        try {
            rabbitMQProducer.sendMessageToFanoutExchange("CREATE_ORDER");
        } catch (Exception e) {
            log.info("Exception occured in sending message {}", e.getMessage());
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    public void updateOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        log.info("Request received from client on grpc server for update order");
        int orderAmount = (int)getOrderAmount(request.getOrderItemsList());
        OrderResponse response = OrderResponse.newBuilder()
                .setOrderId(request.getOrderId())
                .setOrderAmount(orderAmount)
                .setCustomerId(request.getCustomerId())
                .build();

        log.info("OrderAmount is {}", orderAmount);
        try {
            rabbitMQProducer.sendMessageToTopicExchange("update", "UPDATE_ORDER");
        } catch (Exception e) {
            log.info("Exception occured in sending message {}", e.getMessage());
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private float getOrderAmount(List<OrderItem> orderItems) {
        float orderAmount = 0;
        for (OrderItem orderItem: orderItems) {
            Optional<Product> matchingProduct = dataProviderService.getProductById(orderItem.getProductId());
            if (matchingProduct.isPresent()) {
                Double productPrice = matchingProduct.get().getPrice();
                orderAmount+= (float) (orderItem.getQuantity() * productPrice);
            }
            log.info("matching product is {}", matchingProduct);
        }
        return orderAmount;
    }

}
