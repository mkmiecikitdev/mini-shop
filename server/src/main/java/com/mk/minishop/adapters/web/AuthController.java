package com.mk.minishop.adapters.web;

import com.mk.minishop.adapters.security.IoJsonWebTokenCreator;
import com.mk.minishop.auth.AuthContext;
import com.mk.minishop.auth.AuthFacade;
import com.mk.minishop.auth.LoginRegisterFormDto;
import com.mk.minishop.auth.TokenResultDto;
import com.mk.minishop.errors.ApiErrors;
import io.vavr.Function0;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    private ResponseEntity<?> doAuthActionAndGenerateToken(Function0<Either<com.mk.minishop.error.MiniShopError, AuthContext>> authAction, HttpStatus httpStatus) {
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
