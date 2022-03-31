package com.mk.minishop.products;

import com.mk.minishop.error.MiniShopError;
import com.mk.minishop.error.MiniShopErrorType;
import io.vavr.control.Either;

import javax.persistence.Embeddable;

@Embeddable
class Quantity {

    private int quantity;

    private Quantity(int quantity) {
        this.quantity = quantity;
    }

    private Quantity() {

    } // JPA

    static Either<MiniShopError, Quantity> of(int quantity) {
        if (quantity <= 0) {
            return new MiniShopError(MiniShopErrorType.QUANTITY_LESS_OR_EQUAL_ZERO).toEither();
        }

        return Either.right(new Quantity(quantity));
    }

    int asInt() {
        return quantity;
    }
}
