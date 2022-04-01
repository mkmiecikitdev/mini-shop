package com.mk.minishop.server.orders;

import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface OrdersRepository extends Repository<Order, UUID> {

    Order save(Order order);

    long countByUserId(UUID userId);

}
