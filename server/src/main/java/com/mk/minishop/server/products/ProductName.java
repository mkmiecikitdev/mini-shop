package com.mk.minishop.server.products;

import com.mk.minishop.server.error.MiniShopError;
import com.mk.minishop.server.error.MiniShopErrorType;
import io.vavr.control.Either;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
class ProductName {

    private static final int MIN_LENGTH = 2;

    private String name;

    private ProductName(String name) {
        this.name = name;
    }

    private ProductName() {

    } // JPA

    static Either<MiniShopError, ProductName> of(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            return new MiniShopError(MiniShopErrorType.INVALID_PRODUCT_NAME).toEither();
        }

        final String trimmed = name.trim();

        return isTrimmedLoginTooShort(trimmed) ?
                new MiniShopError(MiniShopErrorType.INVALID_PRODUCT_NAME).toEither() :
                Either.right(new ProductName(trimmed));
    }

    private static boolean isTrimmedLoginTooShort(String trimmed) {
        return trimmed.length() < MIN_LENGTH;
    }

    String asString() {
        return name;
    }
}
