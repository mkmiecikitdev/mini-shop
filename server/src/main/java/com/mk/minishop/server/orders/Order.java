package com.mk.minishop.server.orders;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    private UUID id;

    private UUID userId;

    private Order(UUID id, UUID userId) {
        this.id = id;
        this.userId = userId;
    }

    private Order() {

    } // JPA

    static Order create(UUID userId) {
        return new Order(UUID.randomUUID(), userId);
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }
}
