package com.mk.minishop.dsl;

import com.mk.minishop.error.MiniShopError;
import com.mk.minishop.error.MiniShopErrorType;
import io.vavr.control.Either;
import org.assertj.core.api.Assertions;

public class EitherAssertion<R> {

    private final Either<MiniShopError, R> result;

    public EitherAssertion(Either<MiniShopError, R> result) {
        this.result = result;
    }

    public EitherAssertion<R> isError() {
        Assertions.assertThat(result.isLeft()).isTrue();
        return this;
    }

    public EitherAssertion<R> isOk() {
        Assertions.assertThat(result.isRight()).isTrue();
        return this;
    }

    public EitherAssertion<R> withValue(R r) {
        Assertions.assertThat(result.get()).isEqualTo(r);
        return this;
    }

    public EitherAssertion<R> withErrorValue(MiniShopError miniShopError) {
        Assertions.assertThat(result.getLeft()).isEqualTo(miniShopError);
        return this;
    }

    public EitherAssertion<R> withType(MiniShopErrorType type) {
        Assertions.assertThat(result.getLeft().getType()).isEqualTo(type);
        return this;
    }

    public EitherAssertion<R> withMessage(String message) {
        Assertions.assertThat(result.getLeft().getMessage()).isEqualTo(message);
        return this;
    }
}
