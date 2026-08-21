package com.abcbank.redis2.controller;

import com.abcbank.redis2.model.Product;
import com.abcbank.redis2.model.LoginEvent;
import com.abcbank.redis2.service.ProductService;
import com.abcbank.redis2.service.EventPublisher;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final EventPublisher eventPublisher;

    public ProductController(ProductService productService, EventPublisher eventPublisher) {
        this.productService = productService;
        this.eventPublisher = eventPublisher;
    }
    
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @GetMapping("/login")
    public String login() {
        LoginEvent event = new LoginEvent("brian", LocalDateTime.now());
        eventPublisher.publishLoginEvent(event);
        return "Login event published";
    }
}
