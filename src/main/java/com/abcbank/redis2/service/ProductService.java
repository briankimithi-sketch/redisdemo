package com.abcbank.redis2.service;

import com.abcbank.redis2.model.Product;
import com.abcbank.redis2.model.ProductCreatedEvent;
import com.abcbank.redis2.model.ProductDeletedEvent;
import com.abcbank.redis2.model.ProductUpdatedEvent;
import com.abcbank.redis2.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;
    private final EventPublisher eventPublisher;

    public ProductService(
            ProductRepository repo,
            EventPublisher eventPublisher) {

        this.repo = repo;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Get a product by ID.
     * Redis is checked first before querying the database.
     */
    @Cacheable(value = "products", key = "#id")
    public Product getProduct(Long id) {

        System.out.println(
                "Fetching product " + id + " from DB..."
        );

        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found: " + id
                ));
    }

    /**
     * Get all products.
     */
    public List<Product> findAll() {
        return repo.findAll();
    }

    /**
     * Manually evict a product from Redis cache.
     */
    @CacheEvict(value = "products", key = "#id")
    public void evictCache(Long id) {

        System.out.println(
                "Evicting cache for product " + id
        );
    }

    /**
     * Create a new product.
     *
     * Called by ProductCommandListener when it receives
     * a CREATE command from RabbitMQ.
     */
    public Product save(Product product) {

        Product savedProduct = repo.save(product);

        ProductCreatedEvent event = new ProductCreatedEvent(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                LocalDateTime.now()
        );

        eventPublisher.publishProductCreatedEvent(event);

        return savedProduct;
    }

    /**
     * Update an existing product.
     *
     * Called by ProductCommandListener when it receives
     * an UPDATE command from RabbitMQ.
     */
    @CacheEvict(value = "products", key = "#id")
    public Product update(Long id, Product product) {

        Product existingProduct = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found: " + id
                ));

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());

        Product updatedProduct = repo.save(existingProduct);

        ProductUpdatedEvent event = new ProductUpdatedEvent(
                updatedProduct.getId(),
                updatedProduct.getName(),
                updatedProduct.getPrice(),
                LocalDateTime.now()
        );

        eventPublisher.publishProductUpdatedEvent(event);

        return updatedProduct;
    }

    /**
     * Delete an existing product.
     *
     * Returns the deleted Product so that the
     * ProductCommandListener can send it back to
     * the ProductController as the RabbitMQ reply.
     */
    @CacheEvict(value = "products", key = "#id")
    public Product delete(Long id) {

        Product product = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Product not found: " + id
                ));

        repo.delete(product);

        ProductDeletedEvent event = new ProductDeletedEvent(
                product.getId(),
                product.getName(),
                LocalDateTime.now()
        );

        eventPublisher.publishProductDeletedEvent(event);

        System.out.println(
                "Deleted product " + id + " and evicted cache"
        );

        return product;
    }
}
