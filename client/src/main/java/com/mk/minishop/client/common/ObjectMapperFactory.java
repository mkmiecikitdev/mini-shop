package com.mk.minishop.client.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.jackson.datatype.VavrModule;

public class ObjectMapperFactory {

    private ObjectMapperFactory() {
    }

    public static ObjectMapper build() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new VavrModule());
        return objectMapper;
    }
}
