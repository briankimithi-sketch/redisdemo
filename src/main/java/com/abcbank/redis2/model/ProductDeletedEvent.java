package com.abcbank.redis2.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ProductDeletedEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long productId;
    private String name;
    private LocalDateTime timestamp;

    public ProductDeletedEvent() {}

    public ProductDeletedEvent(Long productId, String name, LocalDateTime timestamp) {
        this.productId = productId;
        this.name = name;
        this.timestamp = timestamp;
    }

    
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

   
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDeletedEvent)) return false;
        ProductDeletedEvent that = (ProductDeletedEvent) o;
        return Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }

    @Override
    public String toString() {
        return "ProductDeletedEvent{" +
               "productId=" + productId +
               ", name='" + name + '\'' +
               ", timestamp=" + timestamp +
               '}';
    }
}
