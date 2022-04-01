package com.mk.minishop.server.adapters.security;

import com.mk.minishop.server.auth.AuthContext;
import com.mk.minishop.server.auth.UserContextProvider;
import org.springframework.security.core.context.SecurityContextHolder;

public class FromSCHUserContextProvider implements UserContextProvider {
    @Override
    public AuthContext userContext() {
        return (AuthContext) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
