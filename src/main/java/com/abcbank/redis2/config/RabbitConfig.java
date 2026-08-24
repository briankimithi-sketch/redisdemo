package com.abcbank.redis2.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    
    public static final String EXCHANGE_NAME = "user.events.exchange";

    
    public static final String QUEUE_NAME = "queue-name";
    public static final String ROUTING_KEY = "login.event";

    
    public static final String PRODUCT_QUEUE_NAME = "product.queue";
    public static final String PRODUCT_CREATED_ROUTING_KEY = "product.created";

    
    public static final String PRODUCT_UPDATED_QUEUE_NAME = "product.updated.queue";
    public static final String PRODUCT_UPDATED_ROUTING_KEY = "product.updated";


public static final String PRODUCT_DELETED_QUEUE_NAME = "product.deleted.queue";
public static final String PRODUCT_DELETED_ROUTING_KEY = "product.deleted";

@Bean
public Queue productDeletedQueue() {
    return new Queue(PRODUCT_DELETED_QUEUE_NAME, true);
}

@Bean
public Binding productDeletedBinding(Queue productDeletedQueue, DirectExchange exchange) {
    return BindingBuilder
            .bind(productDeletedQueue)
            .to(exchange)
            .with(PRODUCT_DELETED_ROUTING_KEY);
}


    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    
    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    
    @Bean
    public Queue productQueue() {
        return new Queue(PRODUCT_QUEUE_NAME, true);
    }

    
    @Bean
    public Binding productBinding(Queue productQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(productQueue)
                .to(exchange)
                .with(PRODUCT_CREATED_ROUTING_KEY);
    }

    
    @Bean
    public Queue productUpdatedQueue() {
        return new Queue(PRODUCT_UPDATED_QUEUE_NAME, true);
    }

    
    @Bean
    public Binding productUpdatedBinding(Queue productUpdatedQueue, DirectExchange exchange) {
        return BindingBuilder
                .bind(productUpdatedQueue)
                .to(exchange)
                .with(PRODUCT_UPDATED_ROUTING_KEY);
    }

    
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
