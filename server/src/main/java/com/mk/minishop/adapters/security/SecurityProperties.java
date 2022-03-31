package com.mk.minishop.adapters.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class SecurityProperties {

    @Value("${security.secret}")
    private String secret;

    @Value("${security.expiration-time}")
    private String expirationTime;

    SecurityProperties(String secret, String expirationTime) {
        this.secret = secret;
        this.expirationTime = expirationTime;
    }

    SecurityProperties() {
    }

    String getSecret() {
        return this.secret;
    }

    Long getExpirationTime() {
        return Long.parseLong(expirationTime);
    }
}
