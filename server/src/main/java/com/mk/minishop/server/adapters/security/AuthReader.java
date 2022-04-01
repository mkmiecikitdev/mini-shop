package com.mk.minishop.server.adapters.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.minishop.server.auth.AuthContext;
import io.jsonwebtoken.Jwts;
import io.vavr.control.Option;
import io.vavr.control.Try;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

import static com.mk.minishop.server.adapters.security.SecurityConstants.HEADER_NAME;
import static com.mk.minishop.server.adapters.security.SecurityConstants.TOKEN_PREFIX;

class AuthReader {

    private static final Logger LOG = LoggerFactory.getLogger(AuthReader.class);

    private final ObjectMapper objectMapper;
    private final String secret;

    AuthReader(ObjectMapper objectMapper, String secret) {
        this.objectMapper = objectMapper;
        this.secret = secret;
    }

    Option<? extends Authentication> getAuthFromRequest(HttpServletRequest request) {
        return Option.of(request.getHeader(HEADER_NAME))
                .flatMap(this::getUserContext)
                .map(MiniShopAuthentication::new)
                .onEmpty(() -> LOG.error("Authentication is null"));
    }


    private Option<AuthContext> getUserContext(String token) {
        return Try.of(
                () -> Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody()
                        .getSubject()
        )
                .onFailure(e -> LOG.error("Invalid token", e))
                .flatMapTry(it -> Try.of(() -> objectMapper.readValue(it, AuthContext.class))
                        .onFailure(e -> LOG.error("Cannot read UserContext", e))
                )
                .toOption();
    }
}
