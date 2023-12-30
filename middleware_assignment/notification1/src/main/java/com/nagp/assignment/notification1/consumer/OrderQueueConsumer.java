package com.nagp.assignment.notification1.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderQueueConsumer {
    public void receiveMessage(String message) {
        log.info("Notification service 1 Received message " + message);
    }
}
