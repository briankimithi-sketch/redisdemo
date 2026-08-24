package com.abcbank.redis2.service;

import com.abcbank.redis2.config.RabbitConfig;
import com.abcbank.redis2.model.LoginEvent;
import com.abcbank.redis2.model.ProductCreatedEvent;
import com.abcbank.redis2.model.ProductUpdatedEvent;
import com.abcbank.redis2.model.ProductDeletedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Publishes a login event to RabbitMQ.
     */
    public void publishLoginEvent(LoginEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.ROUTING_KEY,
                event
        );
        System.out.println("Published login event");
    }

    /**
     * Publishes a product-created event to RabbitMQ.
     */
    public void publishProductCreatedEvent(ProductCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.PRODUCT_CREATED_ROUTING_KEY,
                event
        );
        System.out.println("Published product created event: " + event.getName());
    }

    /**
     * Publishes a product-updated event to RabbitMQ.
     */
    public void publishProductUpdatedEvent(ProductUpdatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.PRODUCT_UPDATED_ROUTING_KEY,
                event
        );
        System.out.println("Published product updated event: " + event.getName());
    }

    /**
     * Publishes a product-deleted event to RabbitMQ.
     */
    public void publishProductDeletedEvent(ProductDeletedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE_NAME,
                RabbitConfig.PRODUCT_DELETED_ROUTING_KEY,
                event
        );
        System.out.println("Published product deleted event: " + event.getName());
    }
}
