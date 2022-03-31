package com.mk.minishop.adapters.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.minishop.auth.AuthContext;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static com.mk.minishop.adapters.security.SecurityConstants.TOKEN_PREFIX;

public class IoJsonWebTokenCreator {

    private static final Logger LOG = LoggerFactory.getLogger(IoJsonWebTokenCreator.class);

    private final String secret;
    private final long expirationTime;
    private final ObjectMapper objectMapper;

    public IoJsonWebTokenCreator(String secret, long expirationTime, ObjectMapper objectMapper) {
        this.secret = secret;
        this.expirationTime = expirationTime;
        this.objectMapper = objectMapper;
    }

    public Option<String> generate(AuthContext authContext) {
        return Try.of(
                () -> TOKEN_PREFIX + " " + Jwts.builder()
                        .setSubject(objectMapper.writeValueAsString(authContext))
                        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                        .signWith(SignatureAlgorithm.HS512, secret)
                        .compact()
        )
                .onFailure(e -> LOG.error("Cannot create token", e))
                .toOption();
    }
}
