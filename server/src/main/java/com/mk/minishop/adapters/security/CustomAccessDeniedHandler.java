package com.mk.minishop.adapters.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.minishop.errors.ApiErrors;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    // Jackson JSON serializer instance
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exception
    ) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        response
                .getOutputStream()
                .println(
                        objectMapper.writeValueAsString(ApiErrors.ACCESS_DENIED)
                );
    }
}