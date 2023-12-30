package com.nagp.assignment.microservice.order.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Value("${fanout.exchange}")
    private String fanoutExchange;

    @Value("${topic.exchange}")
    private String topicExchange;


    @Value("${notification1_queue.name}")
    private String notification1QueueName;

    @Value("${notification2_queue.name}")
    private String notification2QueueName;

    @Bean
    Queue notification1Queue() {
        return new Queue(notification1QueueName, true);
    }

    @Bean
    Queue notification2Queue() {
        return new Queue(notification2QueueName, true);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchange);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchange);
    }

    @Bean
    Binding fanoutBinding(Queue notification1Queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(notification1Queue).to(fanoutExchange);
    }

    @Bean
    Binding topicBinding(Queue notification2Queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(notification2Queue).to(topicExchange).with("order.create_update");
    }
}
