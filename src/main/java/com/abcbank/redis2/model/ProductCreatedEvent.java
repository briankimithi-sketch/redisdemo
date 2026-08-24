package com.abcbank.redis2.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ProductCreatedEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long productId;
    private String name;
    private Double price;
    private LocalDateTime timestamp;

    public ProductCreatedEvent() {}

    public ProductCreatedEvent(Long productId, String name, Double price, LocalDateTime timestamp) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.timestamp = timestamp;
    }


    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
