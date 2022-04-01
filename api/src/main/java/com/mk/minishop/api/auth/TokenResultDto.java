package com.mk.minishop.api.auth;

public class TokenResultDto {

    private String token;

    public TokenResultDto(String token) {
        this.token = token;
    }

    public TokenResultDto() {
    }

    public String getToken() {
        return token;
    }
}
