package com.mk.minishop.api.products;

import io.vavr.collection.Set;

public class ProductsDto {

    private Set<ProductDto> products;

    public ProductsDto(Set<ProductDto> products) {
        this.products = products;
    }

    public ProductsDto() {
    }

    public Set<ProductDto> getProducts() {
        return products;
    }
}
