package com.mk.minishop.server.products;

import io.vavr.collection.Set;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface ProductRepository extends Repository<Product, UUID> {

    Product save(Product product);

    Set<Product> findAll();

}
