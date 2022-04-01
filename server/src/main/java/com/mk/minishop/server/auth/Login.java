package com.mk.minishop.server.auth;

import com.mk.minishop.server.error.MiniShopError;
import com.mk.minishop.server.error.MiniShopErrorType;
import io.vavr.control.Either;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
class Login {

    private static final int MIN_LENGTH = 5;
    private String login;

    private Login(String login) {
        this.login = login;
    }

    private Login() {

    } // JPA only

    static Either<MiniShopError, Login> of(String rawLogin) {
        if (Objects.isNull(rawLogin) || rawLogin.isBlank()) {
            return new MiniShopError(MiniShopErrorType.LOGIN_IS_NULL_EMPTY_OR_BLANK).toEither();
        }

        final String trimmed = rawLogin.trim();

        return isTrimmedLoginTooShort(trimmed) ?
                new MiniShopError(MiniShopErrorType.INVALID_LOGIN_FORMAT).toEither() :
                Either.right(new Login(trimmed));
    }

    private static boolean isTrimmedLoginTooShort(String trimmed) {
        return trimmed.length() < MIN_LENGTH;
    }
}
