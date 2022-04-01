package com.mk.minishop.api.orders;

import io.vavr.collection.Set;

public class NewOrderFormDto {

    private Set<ProductOrderFormDto> products;

    public NewOrderFormDto(Set<ProductOrderFormDto> products) {
        this.products = products;
    }

    public Set<ProductOrderFormDto> getProducts() {
        return products;
    }
}
