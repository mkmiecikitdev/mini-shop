package com.mk.minishop.api.products;

import java.math.BigDecimal;

public class NewProductForm {
    private String name;
    private int quantity;
    private BigDecimal price;

    public NewProductForm() {
    }

    public NewProductForm(String name, int quantity, BigDecimal price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
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
