package com.mk.minishop.orders;

import com.mk.minishop.error.MiniShopError;
import com.mk.minishop.error.MiniShopErrorType;
import com.mk.minishop.products.ProductFacade;
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
