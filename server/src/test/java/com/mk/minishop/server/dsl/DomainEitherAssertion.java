package com.mk.minishop.server.dsl;

import com.mk.minishop.server.error.MiniShopError;
import com.mk.minishop.server.error.MiniShopErrorType;
import io.vavr.control.Either;
import org.assertj.core.api.Assertions;

public class DomainEitherAssertion<R> {

    private final Either<MiniShopError, R> result;

    public DomainEitherAssertion(Either<MiniShopError, R> result) {
        this.result = result;
    }

    public DomainEitherAssertion<R> isError() {
        Assertions.assertThat(result.isLeft()).isTrue();
        return this;
    }

    public DomainEitherAssertion<R> isOk() {
        Assertions.assertThat(result.isRight()).isTrue();
        return this;
    }

    public DomainEitherAssertion<R> withValue(R r) {
        Assertions.assertThat(result.get()).isEqualTo(r);
        return this;
    }

    public DomainEitherAssertion<R> withErrorValue(MiniShopError miniShopError) {
        Assertions.assertThat(result.getLeft()).isEqualTo(miniShopError);
        return this;
    }

    public DomainEitherAssertion<R> withType(MiniShopErrorType type) {
        Assertions.assertThat(result.getLeft().getType()).isEqualTo(type);
        return this;
    }

    public DomainEitherAssertion<R> withMessage(String message) {
        Assertions.assertThat(result.getLeft().getMessage()).isEqualTo(message);
        return this;
    }
}
