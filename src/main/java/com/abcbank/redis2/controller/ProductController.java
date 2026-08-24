package com.abcbank.redis2.controller;

import com.abcbank.redis2.model.LoginEvent;
import com.abcbank.redis2.model.Product;
import com.abcbank.redis2.service.EventPublisher;
import com.abcbank.redis2.service.ProductService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final EventPublisher eventPublisher;

    public ProductController(
            ProductService productService,
            EventPublisher eventPublisher) {

        this.productService = productService;
        this.eventPublisher = eventPublisher;
    }

    // GET /products/{id}
    // Get one product by ID
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    // GET /products
    // Get all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    // GET /products/login
    // Publish login event to RabbitMQ
    @GetMapping("/login")
    public String login() {

        LoginEvent event = new LoginEvent(
                "brian",
                LocalDateTime.now()
        );

        eventPublisher.publishLoginEvent(event);

        return "Login event published";
    }

    // POST /products
    // Create a new product
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    // PUT /products/{id}
    // Update an existing product
    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @RequestBody Product product) {

        return productService.update(id, product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {

        productService.delete(id);

        return "Product " + id + " deleted";
    }
}
