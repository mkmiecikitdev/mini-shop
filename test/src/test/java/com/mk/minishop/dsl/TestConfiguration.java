package com.mk.minishop.dsl;

import com.mk.minishop.auth.AuthContext;
import com.mk.minishop.auth.AuthFacade;
import com.mk.minishop.products.ProductFacade;
import io.vavr.collection.HashSet;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;
import java.util.UUID;

public class TestConfiguration {

    private final AuthFacade authFacade;
    private final ProductFacade productFacade;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private AuthContext registeredUser;
    private UUID addedProductId;

    public TestConfiguration(AuthFacade authFacade, ProductFacade productFacade, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.authFacade = authFacade;
        this.productFacade = productFacade;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public TestConfiguration withRegisteredUser() {
        registeredUser = authFacade.register(TestLoginRegisterForms.VALID_LOGIN_REGISTER_FORM).get();
        return this;
    }

    public void clearAll() {
        HashSet.of("users", "products", "orders")
                .forEach(tableName -> namedParameterJdbcTemplate.execute(String.format("delete from %s", tableName), Map.of(), x -> null));
    }

    public AuthContext getRegisteredUser() {
        return registeredUser;
    }
}
