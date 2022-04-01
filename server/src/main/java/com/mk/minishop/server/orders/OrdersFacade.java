package com.mk.minishop.server.orders;

import com.mk.minishop.api.orders.NewOrderFormDto;
import com.mk.minishop.server.error.MiniShopError;
import com.mk.minishop.server.error.MiniShopErrorType;
import com.mk.minishop.server.products.ProductFacade;
import io.vavr.control.Either;

import java.util.UUID;

public class OrdersFacade {

    private final OrdersRepository ordersRepository;
    private final ProductFacade productFacade;

    public OrdersFacade(OrdersRepository ordersRepository, ProductFacade productFacade) {
        this.ordersRepository = ordersRepository;
        this.productFacade = productFacade;
    }

    public Either<MiniShopError, UUID> addOrder(NewOrderFormDto newOrderFormDto, UUID userId) {
        if (!productFacade.areThereInStack(newOrderFormDto)) {
            return new MiniShopError(MiniShopErrorType.PRODUCTS_OUT_OF_STOCK).toEither();
        }

        return Either.right(ordersRepository.save(Order.create(userId))
                .getId());
    }

    public long countByUserId(UUID id) {
        return ordersRepository.countByUserId(id);
    }
}
