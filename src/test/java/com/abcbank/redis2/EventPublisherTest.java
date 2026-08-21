package com.abcbank.redis2;

import com.abcbank.redis2.config.RabbitConfig;
import com.abcbank.redis2.model.LoginEvent;
import com.abcbank.redis2.service.EventPublisher;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;

@SpringBootTest
class EventPublisherTest {

    @Autowired
    private EventPublisher eventPublisher;

    @SpyBean
    private RabbitTemplate rabbitTemplate;

    @Test
    void testPublishLoginEvent() {
        LoginEvent event = new LoginEvent("brian", LocalDateTime.now());

        eventPublisher.publishLoginEvent(event);

        verify(rabbitTemplate).convertAndSend(
            RabbitConfig.EXCHANGE_NAME,
            RabbitConfig.ROUTING_KEY,
            event
        );
    }
}
