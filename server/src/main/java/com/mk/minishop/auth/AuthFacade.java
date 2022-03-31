package com.mk.minishop.auth;

import com.mk.minishop.error.MiniShopError;
import com.mk.minishop.error.MiniShopErrorType;
import io.vavr.control.Either;

public class AuthFacade {

    private final AuthRepository repository;
    private final PasswordEncoderVerifier passwordEncoderVerifier;

    public AuthFacade(AuthRepository repository, PasswordEncoderVerifier passwordEncoderVerifier) {
        this.repository = repository;
        this.passwordEncoderVerifier = passwordEncoderVerifier;
        this.repository.save(Auth.asAdmin(Password.of("admin123", passwordEncoderVerifier).get(), Login.of("admin").get()));
    }

    public Either<MiniShopError, AuthContext> register(LoginRegisterFormDto registerForm) {
        return Login.of(registerForm.getLogin())
                .flatMap(login -> {
                    if (repository.existsByLogin(login)) {
                        return new MiniShopError(MiniShopErrorType.LOGIN_EXIST).toEither();
                    }

                    return Password.of(registerForm.getPassword(), passwordEncoderVerifier)
                            .map(password -> Auth.asUser(password, login))
                            .map(repository::save)
                            .map(Auth::asUserCtx);
                });
    }

    public Either<MiniShopError, AuthContext> login(LoginRegisterFormDto loginForm) {
        return Login.of(loginForm.getLogin())
                .flatMap(login -> repository.findByLogin(login)
                        .toEither(new MiniShopError(MiniShopErrorType.USER_WITH_LOGIN_NOT_FOUND)))
                .flatMap(auth -> {
                    if (auth.isCorrectPass(loginForm.getPassword(), passwordEncoderVerifier)) {
                        return Either.right(auth.asUserCtx());
                    }

                    return new MiniShopError(MiniShopErrorType.INVALID_PASS).toEither();
                });
    }
}
