package com.mk.minishop.server.adapters.web;

import com.mk.minishop.api.auth.LoginRegisterFormDto;
import com.mk.minishop.api.auth.TokenResultDto;
import com.mk.minishop.api.errors.ApiErrors;
import com.mk.minishop.server.adapters.security.IoJsonWebTokenCreator;
import com.mk.minishop.server.auth.AuthContext;
import com.mk.minishop.server.auth.AuthFacade;
import com.mk.minishop.server.error.MiniShopError;
import io.vavr.Function0;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class AuthController {

    private final AuthFacade authFacade;
    private final IoJsonWebTokenCreator tokenCreator;
    private final RestErrorResolver restErrorResolver = new RestErrorResolver();

    AuthController(AuthFacade authFacade, IoJsonWebTokenCreator tokenCreator) {
        this.authFacade = authFacade;
        this.tokenCreator = tokenCreator;
    }

    @PostMapping("/register")
    ResponseEntity<?> register(@RequestBody LoginRegisterFormDto registerForm) {
        return doAuthActionAndGenerateToken(() -> authFacade.register(registerForm), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody LoginRegisterFormDto loginForm) {
        return doAuthActionAndGenerateToken(() -> authFacade.login(loginForm), HttpStatus.OK);
    }

    private ResponseEntity<?> doAuthActionAndGenerateToken(Function0<Either<MiniShopError, AuthContext>> authAction, HttpStatus httpStatus) {
        return authAction.apply()
                .map(tokenCreator::generate)
                .fold(
                        restErrorResolver::resolve,
                        maybeCreatedToken -> maybeCreatedToken.fold(
                                () -> ResponseEntity.badRequest().body(ApiErrors.CANNOT_CREATE_TOKEN),
                                it -> ResponseEntity.status(httpStatus).body(new TokenResultDto(it))
                        )
                );
    }
}
