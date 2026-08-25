package com.abcbank.redis2.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreatedEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long productId;
    private String name;
    private Double price;
    private LocalDateTime timestamp;
}
