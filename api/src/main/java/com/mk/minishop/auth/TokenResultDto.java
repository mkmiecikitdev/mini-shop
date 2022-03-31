package com.mk.minishop.auth;

public class TokenResultDto {

    private final String token;

    public TokenResultDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
