package com.mk.minishop.adapters.security;

import com.mk.minishop.auth.AuthContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class MiniShopAuthentication implements Authentication {

    private final AuthContext authContext;

    public MiniShopAuthentication(AuthContext authContext) {
        this.authContext = authContext;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authContext.getRoles()
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role.name())
                .collect(Collectors.toSet());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authContext;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
