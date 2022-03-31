package com.mk.minishop.errors;

public class RestErrorDto {

    private final String code;

    private final String message;

    public RestErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
