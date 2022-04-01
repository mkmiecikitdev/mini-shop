package com.mk.minishop.test.dsl;

import com.mk.minishop.server.auth.AuthContext;
import com.mk.minishop.server.auth.AuthFacade;
import com.mk.minishop.server.products.ProductFacade;
import io.vavr.collection.HashSet;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

public class TestConfiguration {

    private final AuthFacade authFacade;
    private final ProductFacade productFacade;
    private final JdbcTemplate jdbcTemplate;

    private AuthContext registeredUser;
    private UUID addedProductId;

    public TestConfiguration(AuthFacade authFacade, ProductFacade productFacade, JdbcTemplate jdbcTemplate) {
        this.authFacade = authFacade;
        this.productFacade = productFacade;
        this.jdbcTemplate = jdbcTemplate;
    }

    public TestConfiguration withRegisteredUser() {
        registeredUser = authFacade.register(TestLoginRegisterForms.VALID_LOGIN_REGISTER_FORM).get();
        return this;
    }

    public void clearAll() {
        HashSet.of("users", "products", "orders")
                .forEach(tableName -> jdbcTemplate.execute("delete from " + tableName));
    }

    public AuthContext getRegisteredUser() {
        return registeredUser;
    }
}
