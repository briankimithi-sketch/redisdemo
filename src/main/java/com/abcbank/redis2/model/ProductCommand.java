package com.abcbank.redis2.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCommand implements Serializable {
    private static final long serialVersionUID = 1L;

    private String operation;   // CREATE, UPDATE, DELETE
    private Long productId;
    private String name;
    private Double price;
}
