package com.mk.minishop.server.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AuthConfig {

    @Bean
    AuthFacade authFacade(AuthRepository authRepository, PasswordEncoderVerifier passwordEncoderVerifier) {
        return new AuthFacade(authRepository, passwordEncoderVerifier);
    }
}
