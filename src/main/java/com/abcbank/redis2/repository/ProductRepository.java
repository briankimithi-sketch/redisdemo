package com.abcbank.redis2.repository;

import com.abcbank.redis2.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // ✅ JpaRepository already provides CRUD methods:
    // findById, findAll, save, deleteById, etc.
    // You can add custom queries here if needed, e.g.:
    // Optional<Product> findByName(String name);
}
