package com.mk.minishop.products;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductDto {

    private final UUID id;
    private final String name;
    private final int quantity;
    private final BigDecimal price;

    public ProductDto(UUID id, String name, int quantity, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
