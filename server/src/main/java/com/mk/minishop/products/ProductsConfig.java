package com.mk.minishop.products;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductsConfig {

    @Bean
    public ProductFacade productFacade(ProductRepository productRepository) {
        return new ProductFacade(productRepository);
    }

}
