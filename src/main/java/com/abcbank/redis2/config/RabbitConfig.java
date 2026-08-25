package com.abcbank.redis2.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    // === Event Exchange ===
    public static final String EXCHANGE_NAME = "user.events.exchange";

    // Login event
    public static final String QUEUE_NAME = "queue-name";
    public static final String ROUTING_KEY = "login.event";

    // Product created event
    public static final String PRODUCT_QUEUE_NAME = "product.queue";
    public static final String PRODUCT_CREATED_ROUTING_KEY = "product.created";

    // Product updated event
    public static final String PRODUCT_UPDATED_QUEUE_NAME = "product.updated.queue";
    public static final String PRODUCT_UPDATED_ROUTING_KEY = "product.updated";

    // Product deleted event
    public static final String PRODUCT_DELETED_QUEUE_NAME = "product.deleted.queue";
    public static final String PRODUCT_DELETED_ROUTING_KEY = "product.deleted";

    // === Command Exchange ===
    public static final String PRODUCT_COMMAND_EXCHANGE = "product.commands.exchange";
    public static final String PRODUCT_COMMAND_QUEUE = "product.command.queue";
    public static final String PRODUCT_COMMAND_ROUTING_KEY = "product.command";

    // === Event Exchange Beans ===
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
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Queue productQueue() {
        return new Queue(PRODUCT_QUEUE_NAME, true);
    }

    @Bean
    public Binding productBinding(Queue productQueue, DirectExchange exchange) {
        return BindingBuilder.bind(productQueue).to(exchange).with(PRODUCT_CREATED_ROUTING_KEY);
    }

    @Bean
    public Queue productUpdatedQueue() {
        return new Queue(PRODUCT_UPDATED_QUEUE_NAME, true);
    }

    @Bean
    public Binding productUpdatedBinding(Queue productUpdatedQueue, DirectExchange exchange) {
        return BindingBuilder.bind(productUpdatedQueue).to(exchange).with(PRODUCT_UPDATED_ROUTING_KEY);
    }

    @Bean
    public Queue productDeletedQueue() {
        return new Queue(PRODUCT_DELETED_QUEUE_NAME, true);
    }

    @Bean
    public Binding productDeletedBinding(Queue productDeletedQueue, DirectExchange exchange) {
        return BindingBuilder.bind(productDeletedQueue).to(exchange).with(PRODUCT_DELETED_ROUTING_KEY);
    }

    // === Command Exchange Beans ===
    @Bean
    public DirectExchange productCommandExchange() {
        return new DirectExchange(PRODUCT_COMMAND_EXCHANGE, true, false);
    }

    @Bean
    public Queue productCommandQueue() {
        return new Queue(PRODUCT_COMMAND_QUEUE, true);
    }

    @Bean
    public Binding productCommandBinding(Queue productCommandQueue, DirectExchange productCommandExchange) {
        return BindingBuilder.bind(productCommandQueue).to(productCommandExchange).with(PRODUCT_COMMAND_ROUTING_KEY);
    }

    // === JSON Converter ===
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // === RabbitTemplate with request/reply ===
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                        Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);

        // Wait up to 10 seconds (10000 ms) for a reply
        rabbitTemplate.setReplyTimeout(10000);

        return rabbitTemplate;
    }
}
