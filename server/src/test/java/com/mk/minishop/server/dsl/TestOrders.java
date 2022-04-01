package com.mk.minishop.server.dsl;

import com.mk.minishop.api.orders.NewOrderFormDto;
import com.mk.minishop.api.orders.ProductOrderFormDto;
import io.vavr.collection.HashSet;

import java.util.UUID;

public class TestOrders {

    public static NewOrderFormDto RANDOM_ORDER = new NewOrderFormDto(HashSet.of(new ProductOrderFormDto(UUID.randomUUID(), 5)));

    public static NewOrderFormDto withSingleProduct(UUID productId, int quantity) {
        return new NewOrderFormDto(HashSet.of(new ProductOrderFormDto(productId, quantity)));
    }

    public static NewOrderFormDto withTwoProducts(UUID product1Id, int quantity1, UUID product2Id, int quantity2) {
        return new NewOrderFormDto(HashSet.of(
                new ProductOrderFormDto(product1Id, quantity1),
                new ProductOrderFormDto(product2Id, quantity2)
        ));
    }

}
