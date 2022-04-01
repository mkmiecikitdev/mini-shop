package com.mk.minishop.server.auth;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "USERS")
@Entity
public class Auth {

    @Id
    private UUID id;

    @Embedded
    private Password password;

    @Embedded
    private Login login;

    private boolean isAdmin;

    private Auth(Password password, Login login, boolean isAdmin) {
        this.id = UUID.randomUUID();
        this.password = password;
        this.login = login;
        this.isAdmin = isAdmin;
    }

    static Auth asAdmin(Password password, Login login) {
        return new Auth(password, login, true);
    }

    static Auth asUser(Password password, Login login) {
        return new Auth(password, login, false);
    }

    private Auth() { // JPA only

    }

    boolean isCorrectPass(String rawPass, PasswordEncoderVerifier passwordEncoderVerifier) {
        return passwordEncoderVerifier.matches(rawPass, password);
    }

    AuthContext asUserCtx() {
        return new AuthContext(id, getRoles());
    }

    private Set<Role> getRoles() {
        return isAdmin ? HashSet.of(Role.ADMIN, Role.USER) : HashSet.of(Role.USER);
    }
}
