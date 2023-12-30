package com.nagp.assignment.microservice.order.producer;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQProducer {

    @Value("${fanout.exchange}")
    private String fanoutExchange;

    @Value("${topic.exchange}")
    private String topicExchange;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToFanoutExchange(String message) throws Exception {
        log.info("Before sending message {} to fanout exchange", message);
        rabbitTemplate.setExchange(fanoutExchange);
        rabbitTemplate.convertAndSend(message);
        log.info("Message {} sent successfully to fanout exchange", message);
    }

    public void sendMessageToTopicExchange(String routingKey, String message) throws Exception {
        log.info("Before sending message {} to topic exchange with key {}", message, routingKey);
        rabbitTemplate.setExchange(topicExchange);
        rabbitTemplate.convertAndSend(topicExchange, routingKey, message);
        log.info("Message {} sent successfully to topic exchange", message);
    }
}
