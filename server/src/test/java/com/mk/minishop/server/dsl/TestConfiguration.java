package com.mk.minishop.server.dsl;

import com.mk.minishop.api.products.NewProductForm;
import com.mk.minishop.server.orders.InMemoryOrdersRepository;
import com.mk.minishop.server.orders.OrdersFacade;
import com.mk.minishop.server.orders.OrdersRepository;
import com.mk.minishop.server.products.InMemoryProductsRepository;
import com.mk.minishop.server.products.ProductFacade;
import com.mk.minishop.server.products.ProductRepository;
import io.vavr.collection.List;

import java.util.UUID;

public class TestConfiguration {

    private final ProductRepository productRepository = new InMemoryProductsRepository();
    private final OrdersRepository ordersRepository = new InMemoryOrdersRepository();
    private final ProductFacade productFacade = new ProductFacade(productRepository);
    private final OrdersFacade ordersFacade = new OrdersFacade(ordersRepository, productFacade);

    private List<UUID> savedProductIds = List.empty();

    public TestConfiguration withSampleProducts(NewProductForm newProductForm) {
        final UUID uuid = productFacade.addProduct(newProductForm).get();
        savedProductIds = savedProductIds.append(uuid);
        return this;
    }

    public UUID firstSavedProductId() {
        return savedProductIds.get(0);
    }

    public UUID getProductId(int index) {
        return savedProductIds.get(index);
    }

    public ProductFacade getProductFacade() {
        return productFacade;
    }

    public OrdersFacade getOrdersFacade() {
        return ordersFacade;
    }
}
