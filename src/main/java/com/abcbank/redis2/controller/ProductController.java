package com.abcbank.redis2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.abcbank.redis2.service.EventPublisher;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final EventPublisher eventPublisher;

    public ProductController(EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/{id}")
    public String getProduct(@PathVariable String id) {
        // simulate DB + Redis cache hit
        return "Product " + id;
    }

    @GetMapping("/login")
    public String login() {
        eventPublisher.publishLoginEvent("User logged in: brian");
        return "Login event published";
    }
}
