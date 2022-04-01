package com.mk.minishop.server.products;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import io.vavr.collection.Set;

import java.util.UUID;

public class InMemoryProductsRepository implements ProductRepository {

    private Map<UUID, Product> db = HashMap.empty();

    @Override
    public Product save(Product product) {
        db = db.put(product.dto().getId(), product);
        return product;
    }

    @Override
    public Set<Product> findAll() {
        return db.values().toSet();
    }
}
