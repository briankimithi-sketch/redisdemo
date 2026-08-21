package com.abcbank.redis2.consumer;

import com.abcbank.redis2.config.RabbitConfig;
import com.abcbank.redis2.model.LoginEvent;   // ✅ POJO event class
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void receiveMessage(LoginEvent event) {
        System.out.println("Consumed login event: " 
            + event.getUsername() 
            + " at " + event.getTimestamp());
    }
}
