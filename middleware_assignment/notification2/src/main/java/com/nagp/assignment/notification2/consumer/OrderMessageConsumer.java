package com.nagp.assignment.notification2.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderMessageConsumer implements MessageListener {


    @Override
    public void onMessage(Message message) {
        String receivedMessage = new String(message.getBody());
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        log.info("Received message {} by notification service 2 on routing key {} ", receivedMessage, routingKey);
    }
}
