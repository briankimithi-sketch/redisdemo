package com.abcbank.redis2;

import com.abcbank.redis2.config.RabbitConfig;
import com.abcbank.redis2.model.LoginEvent;
import com.abcbank.redis2.service.EventPublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private EventPublisher eventPublisher;

    @Test
    void testPublishLoginEvent() {
        // Arrange
        LoginEvent event = new LoginEvent("brian", LocalDateTime.now());

        // Act
        eventPublisher.publishLoginEvent(event);

        // Assert
        verify(rabbitTemplate).convertAndSend(
            eq(RabbitConfig.EXCHANGE_NAME),
            eq(RabbitConfig.ROUTING_KEY),
            eq(event)
        );
    }
}

