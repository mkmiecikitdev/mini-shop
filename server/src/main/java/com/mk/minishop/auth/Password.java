package com.mk.minishop.auth;

import io.vavr.control.Either;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Password {

    private String password;

    private Password(String password) {
        this.password = password;
    }

    private Password() {

    } // JPA only

    public static Either<com.mk.minishop.error.MiniShopError, Password> of(String rawPassword, PasswordEncoderVerifier passwordEncoderVerifier) {
        if (Objects.isNull(rawPassword) || rawPassword.isBlank()) {
            return new com.mk.minishop.error.MiniShopError(com.mk.minishop.error.MiniShopErrorType.PASS_IS_NULL_EMPTY_OR_BLANK).toEither();
        }

        if (rawPassword.length() < 8) {
            return new com.mk.minishop.error.MiniShopError(com.mk.minishop.error.MiniShopErrorType.INVALID_PASS_FORMAT).toEither();
        }

        return Either.right(new Password(passwordEncoderVerifier.encode(rawPassword)));
    }

    public String asString() {
        return this.password;
    }
}
