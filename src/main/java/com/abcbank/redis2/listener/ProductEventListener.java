package com.abcbank.redis2.listener;

import com.abcbank.redis2.model.ProductCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventListener {

    @RabbitListener(queues = "product.queue")
    public void handleProductCreated(ProductCreatedEvent event) {

        System.out.println(
                "Consumed product created event: "
                        + event.getName()
                        + " at "
                        + event.getTimestamp()
        );
    }
}
