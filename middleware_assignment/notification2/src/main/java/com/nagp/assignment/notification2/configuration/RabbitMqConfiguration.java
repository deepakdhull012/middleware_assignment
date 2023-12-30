package com.nagp.assignment.notification2.configuration;


import com.nagp.assignment.notification2.consumer.OrderMessageConsumer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {


    @Value("${notification2_queue.name}")
    private String notification2QueueName;

    @Value("${fanout.exchange}")
    private String fanoutExchange;

    @Value("${topic.exchange}")
    private String topicExchange;

    @Bean
    Queue notification2Queue() {
        return new Queue(notification2QueueName, true);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(topicExchange);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchange);
    }

    @Bean
    Binding topicBinding(Queue notification2Queue, TopicExchange topicExchange) {
        return BindingBuilder.bind(notification2Queue).to(topicExchange).with("update");
    }

    @Bean
    Binding notification2FanoutBinding(Queue notification2Queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(notification2Queue).to(fanoutExchange);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             OrderMessageConsumer orderMessageConsumer) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(notification2QueueName);
        container.setMessageListener(orderMessageConsumer);
        return container;
    }
}
