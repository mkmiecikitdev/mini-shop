package com.mk.minishop;

import com.mk.minishop.adapters.security.SecurityProperties;
import com.mk.minishop.auth.AuthClient;
import com.mk.minishop.auth.AuthFacade;
import com.mk.minishop.dsl.TestConfiguration;
import com.mk.minishop.dsl.TestTokenCreator;
import com.mk.minishop.products.ProductFacade;
import com.mk.minishop.products.ProductsClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MiniShopApplication.class)
public class BaseIT {

    @LocalServerPort
    private int port;

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private SecurityProperties securityProperties;

    protected AuthClient authClient;

    protected ProductsClient productsClient;

    protected TestConfiguration testConfiguration;

    protected TestTokenCreator testTokenCreator;

    @BeforeEach
    public void setup() {
        authClient = new AuthClient("localhost", port);
        productsClient = new ProductsClient("localhost", port);
        testConfiguration = new TestConfiguration(authFacade, productFacade, namedParameterJdbcTemplate);
        testTokenCreator = new TestTokenCreator(securityProperties);
    }

    @AfterEach
    public void clean() {
        testConfiguration.clearAll();
    }

}