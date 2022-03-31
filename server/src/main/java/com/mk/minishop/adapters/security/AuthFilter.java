package com.mk.minishop.adapters.security;

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
                .peek(it -> SecurityContextHolder.getContext().setAuthentication(it))
                .onEmpty(() -> Try.run(() -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Cannot authorize")));

        filterChain.doFilter(request, response);
    }
}
