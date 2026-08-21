package com.abcbank.redis2.service;

import com.abcbank.redis2.model.Product;
import com.abcbank.redis2.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    // ✅ Cache product lookups in Redis
    @Cacheable(value = "products", key = "#id")
    public Product getProduct(Long id) {
        System.out.println("Fetching product " + id + " from DB...");
        return repo.findById(id)
                   .orElseThrow(() -> new IllegalArgumentException("Product not found: " + id));
    }

    // ✅ Evict product cache entry when product is updated or removed
    @CacheEvict(value = "products", key = "#id")
    public void evictCache(Long id) {
        System.out.println("Evicting cache for product " + id);
    }
}
