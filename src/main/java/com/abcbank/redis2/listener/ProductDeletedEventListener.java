package com.abcbank.redis2.listener;

import com.abcbank.redis2.config.RabbitConfig;
import com.abcbank.redis2.model.ProductDeletedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProductDeletedEventListener {

    @RabbitListener(queues = RabbitConfig.PRODUCT_DELETED_QUEUE_NAME)
    public void handleProductDeleted(ProductDeletedEvent event) {
        System.out.println("Consumed product deleted event: " + event);
        
    }
}
