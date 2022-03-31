package com.mk.minishop.error;

import io.vavr.control.Either;

public class MiniShopError {

    private final MiniShopErrorType type;
    private final String message;

    public MiniShopError(MiniShopErrorType type, String message) {
        this.type = type;
        this.message = message;
    }

    public MiniShopError(MiniShopErrorType type) {
        this.type = type;
        this.message = type.getDefaultMessage();
    }

    public MiniShopErrorType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public <R> Either<com.mk.minishop.error.MiniShopError, R> toEither() {
        return Either.left(this);
    }
}
