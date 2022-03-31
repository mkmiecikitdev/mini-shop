package com.mk.minishop.errors;

public class ApiErrors {

    private ApiErrors() {
    }

    public static RestErrorDto CANNOT_CREATE_TOKEN = new RestErrorDto("CANNOT_CREATE_TOKEN", "Cannot create token");
    public static RestErrorDto ACCESS_DENIED = new RestErrorDto("ACCESS_DENIED", "User can not access this resource");
}
