package com.mk.minishop.products;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "PRODUCTS")
public class Product {

    @Id
    private UUID id;

    @Embedded
    private Quantity quantity;

    @Embedded
    private ProductName productName;

    @Embedded
    private Price price;

    private Product(UUID id, Quantity quantity, ProductName productName, Price price) {
        this.id = id;
        this.quantity = quantity;
        this.productName = productName;
        this.price = price;
    }

    private Product() {

    } // JPA ONLY

    static Product create(Quantity quantity, ProductName productName, Price price) {
        return new Product(UUID.randomUUID(), quantity, productName, price);
    }

    ProductDto dto() {
        return new ProductDto(id, productName.asString(), quantity.asInt(), price.asBigDecimal());
    }
}
