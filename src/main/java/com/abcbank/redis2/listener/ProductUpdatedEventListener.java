package com.abcbank.redis2.listener;

import com.abcbank.redis2.config.RabbitConfig;
import com.abcbank.redis2.model.ProductUpdatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdatedEventListener {

    @RabbitListener(queues = RabbitConfig.PRODUCT_UPDATED_QUEUE_NAME)
    public void handleProductUpdated(ProductUpdatedEvent event) {

        System.out.println(
                "Consumed product updated event: "
                        + event.getName()
                        + " at "
                        + event.getTimestamp()
        );
    }
}
