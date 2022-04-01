package com.mk.minishop.server.orders;

import com.mk.minishop.server.dsl.DomainEitherAssertion;
import com.mk.minishop.server.dsl.TestConfiguration;
import com.mk.minishop.server.dsl.TestOrders;
import com.mk.minishop.server.dsl.TestProducts;
import com.mk.minishop.server.error.MiniShopError;
import com.mk.minishop.server.error.MiniShopErrorType;
import io.vavr.control.Either;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class OrdersFacadeTest {

    private TestConfiguration testConfiguration;
    private OrdersFacade ordersFacade;

    @BeforeEach
    public void setup() {
        testConfiguration = new TestConfiguration();
        ordersFacade = testConfiguration.getOrdersFacade();
    }

    @Test
    public void shouldReturnNoOrder() {
        Assertions.assertThat(ordersFacade.countByUserId(UUID.randomUUID())).isEqualTo(0);
    }

    @Test
    public void shouldReturnErrorIfNoProductsInStack() {
        final Either<MiniShopError, UUID> result = ordersFacade.addOrder(TestOrders.RANDOM_ORDER, UUID.randomUUID());

        new DomainEitherAssertion<>(result)
                .isError()
                .withType(MiniShopErrorType.PRODUCTS_OUT_OF_STOCK);

        Assertions.assertThat(ordersFacade.countByUserId(UUID.randomUUID())).isEqualTo(0);
    }

    @Test
    public void shouldCreateOrder() {
        testConfiguration.withSampleProducts(TestProducts.BALLS_WITH_QUANTITY_5);
        final UUID ballId = testConfiguration.firstSavedProductId();
        final UUID userId = UUID.randomUUID();

        final Either<MiniShopError, UUID> result = ordersFacade.addOrder(TestOrders.withSingleProduct(ballId, 5), userId);

        new DomainEitherAssertion<>(result)
                .isOk();

        Assertions.assertThat(ordersFacade.countByUserId(userId)).isEqualTo(1);
    }

    @Test
    public void shouldReturnErrorIfProductExistsButQuantityIsNotEnough() {
        testConfiguration.withSampleProducts(TestProducts.BALLS_WITH_QUANTITY_5);
        testConfiguration.withSampleProducts(TestProducts.SHOES_WITH_QUANTITY_2);
        final UUID ballId = testConfiguration.getProductId(0);
        final UUID shoesId = testConfiguration.getProductId(1);
        final UUID userId = UUID.randomUUID();

        final Either<MiniShopError, UUID> result = ordersFacade.addOrder(TestOrders.withTwoProducts(ballId, 5, shoesId, 5), userId);

        new DomainEitherAssertion<>(result)
                .isError()
                .withType(MiniShopErrorType.PRODUCTS_OUT_OF_STOCK)
                .withMessage("Products out of stack");

        Assertions.assertThat(ordersFacade.countByUserId(userId)).isEqualTo(0);
    }
}
