package com.abcbank.redis2.repository;

import com.abcbank.redis2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    
}
