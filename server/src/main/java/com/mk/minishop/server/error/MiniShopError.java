package com.mk.minishop.server.error;

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

    public <R> Either<com.mk.minishop.server.error.MiniShopError, R> toEither() {
        return Either.left(this);
    }
}
