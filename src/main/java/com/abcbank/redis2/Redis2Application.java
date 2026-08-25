package com.abcbank.redis2;

import com.abcbank.redis2.model.Product;
import com.abcbank.redis2.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class Redis2Application {

    public static void main(String[] args) {
        SpringApplication.run(Redis2Application.class, args);
    }

    @Bean
    CommandLineRunner runner(ProductRepository repo) {
        return args -> {
            repo.save(new Product("Laptop", Double.valueOf(1200.0)));
            repo.save(new Product("Phone", Double.valueOf(800.0)));
            repo.save(new Product("Tablet", Double.valueOf(500.0)));
        };
    }
}
