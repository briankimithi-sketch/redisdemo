package com.abcbank.redis2;

import com.abcbank.redis2.config.RabbitConfig;
import com.abcbank.redis2.service.EventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EventPublisherTest {

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Test
    void testPublishLoginEvent() {
        String message = "User logged in: brian";

        String testQueue = "test-queue-" + UUID.randomUUID();

        Queue queue = new Queue(testQueue, false, true, true);

        Binding binding = BindingBuilder
                .bind(queue)
                .to(new org.springframework.amqp.core.DirectExchange(
                        RabbitConfig.EXCHANGE_NAME
                ))
                .with(RabbitConfig.ROUTING_KEY);

        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);

        eventPublisher.publishLoginEvent(message);

        String received = (String) rabbitTemplate.receiveAndConvert(
                testQueue,
                5000
        );

        assertThat(received).isEqualTo(message);

        rabbitAdmin.deleteQueue(testQueue);
        }
}
