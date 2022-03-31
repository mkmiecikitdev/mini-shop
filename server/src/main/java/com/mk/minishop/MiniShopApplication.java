package com.mk.minishop;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class MiniShopApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiniShopApplication.class, args);
    }

    @Primary
    @Bean
    ObjectMapper vavrMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new VavrModule());
        return objectMapper;
    }
}