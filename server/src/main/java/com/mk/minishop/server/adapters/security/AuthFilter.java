package com.mk.minishop.server.adapters.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.minishop.api.errors.ApiErrors;
import io.vavr.control.Try;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

class AuthFilter extends OncePerRequestFilter {

    private final AuthReader authReader;

    AuthFilter(AuthReader authReader) {
        this.authReader = authReader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String uri = request.getRequestURI();
        if (uri.equals("/login") || uri.equals("/register")) {
            filterChain.doFilter(request, response);
            return;
        }

        authReader.getAuthFromRequest(request)
                .peek(it -> {
                    SecurityContextHolder.getContext().setAuthentication(it);
                    Try.run(() -> filterChain.doFilter(request, response));
                })
                .onEmpty(() -> Try.run(() -> {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response
                            .getOutputStream()
                            .println(
                                    new ObjectMapper().writeValueAsString(ApiErrors.ACCESS_DENIED)
                            );
                }));
    }
}
