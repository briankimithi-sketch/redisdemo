package com.abcbank.redis2.service;

import com.abcbank.redis2.model.Product;
import com.abcbank.redis2.repository.ProductRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }


    @Cacheable(value = "products", key = "#id")
    public Product getProduct(Long id) {
        System.out.println("Fetching product " + id + " from DB...");
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Product not found: " + id
                ));
    }

    @CacheEvict(value = "products", key = "#id")
    public void evictCache(Long id) {
        System.out.println("Evicting cache for product " + id);
    }
}
