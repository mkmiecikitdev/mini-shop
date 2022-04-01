package com.mk.minishop.server.adapters.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityProperties {

    @Value("${security.secret}")
    private String secret;

    @Value("${security.expiration-time}")
    private String expirationTime;

    public SecurityProperties(String secret, String expirationTime) {
        this.secret = secret;
        this.expirationTime = expirationTime;
    }

    SecurityProperties() {
    }

    public String getSecret() {
        return this.secret;
    }

    public Long getExpirationTime() {
        return Long.parseLong(expirationTime);
    }
}
