package com.abcbank.redis2.consumer;

import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import com.abcbank.redis2.config.RabbitConfig;

@Component
public class QueueConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Consumed message: " + message);
    }
}
