package com.mk.minishop.auth;

import com.mk.minishop.BaseIT;
import com.mk.minishop.dsl.ClientEitherAssertion;
import com.mk.minishop.dsl.TestLoginRegisterForms;
import com.mk.minishop.error.RestRequestError;
import io.vavr.control.Either;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthClientTest extends BaseIT {

    @Test
    public void shouldRegisterUser() {
        // given

        // when
        final Either<RestRequestError, TokenResultDto> result = authClient.register(TestLoginRegisterForms.VALID_LOGIN_REGISTER_FORM);

        // then
        new ClientEitherAssertion<>(result)
                .isOk()
                .andAssertThat(it -> Assertions.assertThat(it.getToken()).isNotNull());
    }

    @Test
    public void shouldNotRegisterUser() {
        // given

        // when
        final Either<RestRequestError, TokenResultDto> result = authClient.register(TestLoginRegisterForms.REGISTER_FORM_PASS_INVALID_FORMAT);

        // then
        new ClientEitherAssertion<>(result)
                .isError()
                .withErrorCode("INVALID_PASS_FORMAT")
                .withErrorMessage("Password should have more than 8 chars")
                .withErrorHttpStatus(400);
    }

    @Test
    public void shouldLoginUser() {
        // given
        testConfiguration.withRegisteredUser();

        // when
        final Either<RestRequestError, TokenResultDto> result = authClient.login(TestLoginRegisterForms.VALID_LOGIN_REGISTER_FORM);

        // then
        new ClientEitherAssertion<>(result)
                .isOk()
                .andAssertThat(it -> Assertions.assertThat(it.getToken()).isNotNull());
    }

    @Test
    public void shouldNotLoginBecauseOfLoginNotFound() {
        // given
        testConfiguration.withRegisteredUser();

        // when
        final Either<RestRequestError, TokenResultDto> result = authClient.login(TestLoginRegisterForms.LOGIN_FORM_INVALID_LOGIN);

        // then
        new ClientEitherAssertion<>(result)
                .isError()
                .withErrorCode("USER_WITH_LOGIN_NOT_FOUND")
                .withErrorMessage("User not found")
                .withErrorHttpStatus(404);
    }

    @Test
    public void shouldNotLoginBecauseOfInvalidPass() {
        // given
        testConfiguration.withRegisteredUser();

        // when
        final Either<RestRequestError, TokenResultDto> result = authClient.login(TestLoginRegisterForms.LOGIN_FORM_INVALID_PASS);

        // then
        new ClientEitherAssertion<>(result)
                .isError()
                .withErrorCode("INVALID_PASS")
                .withErrorMessage("Invalid password")
                .withErrorHttpStatus(401);
    }
}
