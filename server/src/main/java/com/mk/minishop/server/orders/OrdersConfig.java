package com.mk.minishop.server.orders;

import com.mk.minishop.server.products.ProductFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrdersConfig {

    @Bean
    public OrdersFacade ordersFacade(OrdersRepository ordersRepository, ProductFacade productFacade) {
        return new OrdersFacade(ordersRepository, productFacade);
    }
}
