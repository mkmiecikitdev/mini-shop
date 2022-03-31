package com.mk.minishop.dsl;

import com.mk.minishop.orders.InMemoryOrdersRepository;
import com.mk.minishop.orders.OrdersFacade;
import com.mk.minishop.orders.OrdersRepository;
import com.mk.minishop.products.InMemoryProductsRepository;
import com.mk.minishop.products.NewProductForm;
import com.mk.minishop.products.ProductFacade;
import com.mk.minishop.products.ProductRepository;
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
