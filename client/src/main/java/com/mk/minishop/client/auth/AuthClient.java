package com.mk.minishop.client.auth;

import com.mk.minishop.api.auth.LoginRegisterFormDto;
import com.mk.minishop.api.auth.TokenResultDto;
import com.mk.minishop.client.AbstractClient;
import com.mk.minishop.client.error.RestRequestError;
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
