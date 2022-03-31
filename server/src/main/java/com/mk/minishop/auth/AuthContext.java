package com.mk.minishop.auth;

import io.vavr.collection.Set;

import java.util.UUID;

public class AuthContext {

    private UUID id;
    private Set<Role> roles;

    public AuthContext(UUID id, Set<Role> roles) {
        this.id = id;
        this.roles = roles;
    }

    private AuthContext() {

    }

    public UUID getId() {
        return id;
    }

    public Set<Role> getRoles() {
        return roles;
    }
}
