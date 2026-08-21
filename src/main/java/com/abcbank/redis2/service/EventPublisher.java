package com.abcbank.redis2.service;

import com.abcbank.redis2.config.RabbitConfig;
import com.abcbank.redis2.model.LoginEvent;   // ✅ POJO event class
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    // ✅ Publish a structured LoginEvent object (serialized to JSON)
    public void publishLoginEvent(LoginEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitConfig.EXCHANGE_NAME,
            RabbitConfig.ROUTING_KEY,
            event
        );
    }
}
