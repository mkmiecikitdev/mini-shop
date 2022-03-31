package com.mk.minishop.orders;

import io.vavr.collection.HashMap;
import io.vavr.collection.Map;

import java.util.UUID;

public class InMemoryOrdersRepository implements OrdersRepository {

    private Map<UUID, Order> db = HashMap.empty();

    @Override
    public Order save(Order order) {
        db = db.put(order.getId(), order);
        return order;
    }

    @Override
    public long countByUserId(UUID userId) {
        return db.values()
                .count(it -> it.getUserId() == userId);
    }
}
