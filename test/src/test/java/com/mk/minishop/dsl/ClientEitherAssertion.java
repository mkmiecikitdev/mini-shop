package com.mk.minishop.dsl;

import com.mk.minishop.error.RestRequestError;
import io.vavr.CheckedConsumer;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.assertj.core.api.Assertions;


public class ClientEitherAssertion<R> {

    private final Either<RestRequestError, R> result;

    public ClientEitherAssertion(Either<RestRequestError, R> result) {
        this.result = result;
    }

    public ClientEitherAssertion<R> isError() {
        Assertions.assertThat(result.isLeft()).isTrue();
        return this;
    }

    public ClientEitherAssertion<R> isOk() {
        Assertions.assertThat(result.isRight()).isTrue();
        return this;
    }

    public ClientEitherAssertion<R> withValue(R r) {
        Assertions.assertThat(result.get()).isEqualTo(r);
        return this;
    }

    public ClientEitherAssertion<R> andAssertThat(CheckedConsumer<R> assertions) {
        Try.run(() -> assertions.accept(result.get()));
        return this;
    }

    public ClientEitherAssertion<R> withErrorValue(RestRequestError restRequestError) {
        Assertions.assertThat(result.getLeft()).isEqualTo(restRequestError);
        return this;
    }

    public ClientEitherAssertion<R> withErrorCode(String code) {
        Assertions.assertThat(result.getLeft().getCode()).isEqualTo(code);
        return this;
    }

    public ClientEitherAssertion<R> withErrorHttpStatus(int status) {
        Assertions.assertThat(result.getLeft().getHttpStatus()).isEqualTo(status);
        return this;
    }

    public ClientEitherAssertion<R> withErrorMessage(String message) {
        Assertions.assertThat(result.getLeft().getMessage()).isEqualTo(message);
        return this;
    }
}
