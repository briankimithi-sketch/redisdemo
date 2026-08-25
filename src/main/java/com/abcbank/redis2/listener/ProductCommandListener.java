package com.abcbank.redis2.listener;

import com.abcbank.redis2.model.Product;
import com.abcbank.redis2.model.ProductCommand;
import com.abcbank.redis2.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProductCommandListener {

    private final ProductService productService;

    public ProductCommandListener(ProductService productService) {
        this.productService = productService;
    }

    @RabbitListener(queues = "product.command.queue")
    public Product handleProductCommand(ProductCommand command) {
        switch (command.getOperation()) {
            case "CREATE":
                Product newProduct = new Product();
                newProduct.setName(command.getName());
                newProduct.setPrice(command.getPrice());
                return productService.save(newProduct);

            case "UPDATE":
                Product existingProduct = productService.getProduct(command.getProductId());
                existingProduct.setName(command.getName());
                existingProduct.setPrice(command.getPrice());
                return productService.update(command.getProductId(), existingProduct);

            case "DELETE":
                // ProductService.delete() should now return Product
                Product deleted = productService.delete(command.getProductId());
                return deleted;

            default:
                // Unknown operation → return null, controller will handle with 500
                return null;
        }
    }
}
