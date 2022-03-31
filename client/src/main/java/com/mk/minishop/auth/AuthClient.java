package com.mk.minishop.auth;

import com.mk.minishop.AbstractClient;
import com.mk.minishop.error.RestRequestError;
import io.vavr.control.Either;


public class AuthClient extends AbstractClient {
    public static final String REGISTER_PATH = "/register";
    public static final String LOGIN_PATH = "/login";


    public AuthClient(String host, int port) {
        super(host, port);
    }

    public Either<RestRequestError, TokenResultDto> register(LoginRegisterFormDto loginRegisterFormDto) {
        return postUnauthorized(REGISTER_PATH, loginRegisterFormDto, TokenResultDto.class);
    }

    public Either<RestRequestError, TokenResultDto> login(LoginRegisterFormDto loginRegisterFormDto) {
        return postUnauthorized(LOGIN_PATH, loginRegisterFormDto, TokenResultDto.class);
    }
}
