package com.mk.minishop.adapters.security;

import com.mk.minishop.auth.AuthContext;
import com.mk.minishop.auth.UserContextProvider;
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
