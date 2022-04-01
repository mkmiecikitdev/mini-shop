package com.mk.minishop.api.errors;

public class RestErrorDto {

    private String code;

    private String message;

    public RestErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestErrorDto() {
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
