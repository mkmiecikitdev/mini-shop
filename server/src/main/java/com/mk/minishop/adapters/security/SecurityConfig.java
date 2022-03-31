package com.mk.minishop.adapters.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.minishop.auth.PasswordEncoderVerifier;
import com.mk.minishop.auth.UserContextProvider;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .addFilterBefore(new AuthFilter(new AuthReader(objectMapperWithVavr(), securityProperties.getSecret())), BasicAuthenticationFilter.class)
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/register").permitAll()
                .antMatchers(HttpMethod.GET, "/products").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/products").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/orders").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/orders/count").hasAnyRole("USER", "ADMIN")
                .anyRequest().hasRole("ADMIN")
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler());
    }

    @Bean
    public IoJsonWebTokenCreator ioJsonWebTokenCreator(SecurityProperties securityProperties) {
        return new IoJsonWebTokenCreator(securityProperties.getSecret(), securityProperties.getExpirationTime(), objectMapperWithVavr());
    }

    @Bean
    UserContextProvider userContextProvider() {
        return new FromSCHUserContextProvider();
    }

    @Bean
    PasswordEncoderVerifier passwordEncoderVerifier() {
        return new BCPasswordEncoderVerifier();
    }

    private ObjectMapper objectMapperWithVavr() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new VavrModule());
        return objectMapper;
    }
}
