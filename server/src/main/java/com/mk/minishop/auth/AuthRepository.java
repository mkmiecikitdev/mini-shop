package com.mk.minishop.auth;

import io.vavr.control.Option;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface AuthRepository extends Repository<Auth, UUID> {

    Auth save(Auth auth);

    Option<Auth> findByLogin(Login login);

    boolean existsByLogin(Login login);

}
