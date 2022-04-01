package com.mk.minishop.test;

import com.mk.minishop.client.auth.AuthClient;
import com.mk.minishop.client.products.ProductsClient;
import com.mk.minishop.MiniShopApplication;
import com.mk.minishop.server.adapters.security.SecurityProperties;
import com.mk.minishop.server.auth.AuthFacade;
import com.mk.minishop.server.products.ProductFacade;
import com.mk.minishop.test.dsl.TestConfiguration;
import com.mk.minishop.test.dsl.TestTokenCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MiniShopApplication.class)
public class BaseIT {

    @LocalServerPort
    private int port;

    @Autowired
    private AuthFacade authFacade;

    @Autowired
    private ProductFacade productFacade;

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        testConfiguration = new TestConfiguration(authFacade, productFacade, jdbcTemplate);
        testTokenCreator = new TestTokenCreator(securityProperties);
    }

    @AfterEach
    public void clean() {
        testConfiguration.clearAll();
    }

}