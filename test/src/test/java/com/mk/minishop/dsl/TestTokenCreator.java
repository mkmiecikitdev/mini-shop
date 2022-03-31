package com.mk.minishop.dsl;

import com.mk.minishop.adapters.security.IoJsonWebTokenCreator;
import com.mk.minishop.adapters.security.SecurityConfig;
import com.mk.minishop.adapters.security.SecurityProperties;
import com.mk.minishop.auth.AuthContext;
import com.mk.minishop.auth.Role;
import io.vavr.collection.HashSet;

import java.util.UUID;

public class TestTokenCreator {

    private final IoJsonWebTokenCreator validTokenCreator;
    private final IoJsonWebTokenCreator invalidTokenCreator;

    public TestTokenCreator(SecurityProperties securityProperties) {
        this.validTokenCreator = new SecurityConfig().ioJsonWebTokenCreator(securityProperties);
        this.invalidTokenCreator = new SecurityConfig().ioJsonWebTokenCreator(new SecurityProperties("someOtherSecret", String.valueOf(securityProperties.getExpirationTime())));
    }

    public String generateForUser(UUID id) {
        return validTokenCreator.generate(new AuthContext(id, HashSet.of(Role.USER))).get();
    }

    public String generateForAdmin(UUID id) {
        return validTokenCreator.generate(new AuthContext(id, HashSet.of(Role.USER, Role.ADMIN))).get();
    }

    public String generateInvalid() {
        return invalidTokenCreator.generate(new AuthContext(UUID.randomUUID(), HashSet.of(Role.USER, Role.ADMIN))).get();
    }
}
