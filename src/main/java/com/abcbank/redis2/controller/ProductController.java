package com.abcbank.redis2.controller;

import com.abcbank.redis2.config.RabbitConfig;
import com.abcbank.redis2.model.LoginEvent;
import com.abcbank.redis2.model.Product;
import com.abcbank.redis2.model.ProductCommand;
import com.abcbank.redis2.service.EventPublisher;
import com.abcbank.redis2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final EventPublisher eventPublisher;
    private final RabbitTemplate rabbitTemplate;

    // GET /products/{id}
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    // GET /products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    // GET /products/login
    @GetMapping("/login")
    public String login() {
        LoginEvent event = new LoginEvent("brian", LocalDateTime.now());
        eventPublisher.publishLoginEvent(event);
        return "Login event published";
    }

    // POST /products
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        ProductCommand command = new ProductCommand("CREATE", null, product.getName(), product.getPrice());
        Object response = rabbitTemplate.convertSendAndReceive(
                RabbitConfig.PRODUCT_COMMAND_EXCHANGE,
                RabbitConfig.PRODUCT_COMMAND_ROUTING_KEY,
                command
        );
        if (response == null) return ResponseEntity.internalServerError().build();
        return ResponseEntity.ok((Product) response);
    }

    // PUT /products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        ProductCommand command = new ProductCommand("UPDATE", id, product.getName(), product.getPrice());
        Object response = rabbitTemplate.convertSendAndReceive(
                RabbitConfig.PRODUCT_COMMAND_EXCHANGE,
                RabbitConfig.PRODUCT_COMMAND_ROUTING_KEY,
                command
        );
        if (response == null) return ResponseEntity.internalServerError().build();
        return ResponseEntity.ok((Product) response);
    }

    // DELETE /products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) {
        ProductCommand command = new ProductCommand("DELETE", id, null, null);
        Object response = rabbitTemplate.convertSendAndReceive(
                RabbitConfig.PRODUCT_COMMAND_EXCHANGE,
                RabbitConfig.PRODUCT_COMMAND_ROUTING_KEY,
                command
        );
        if (response == null) return ResponseEntity.internalServerError().build();
        return ResponseEntity.ok((Product) response);
    }
}
