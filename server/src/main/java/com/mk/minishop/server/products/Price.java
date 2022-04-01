package com.mk.minishop.server.products;

import com.mk.minishop.server.error.MiniShopError;
import com.mk.minishop.server.error.MiniShopErrorType;
import io.vavr.control.Either;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
class Price {

    private BigDecimal price;

    private Price(BigDecimal price) {
        this.price = price;
    }

    private Price() {

    } // JPA

    static Either<MiniShopError, Price> of(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            return new MiniShopError(MiniShopErrorType.PRICE_LESS_OR_EQUAL_ZERO).toEither();
        }

        return Either.right(new Price(price));
    }

    BigDecimal asBigDecimal() {
        return price;
    }
}
