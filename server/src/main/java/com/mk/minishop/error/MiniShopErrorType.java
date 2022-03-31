package com.mk.minishop.error;

public enum MiniShopErrorType {

    PASS_IS_NULL_EMPTY_OR_BLANK("Password can not be null, empty or blank"),
    INVALID_PASS_FORMAT("Password should have more than 8 chars"),

    LOGIN_IS_NULL_EMPTY_OR_BLANK("Password can not be null, empty or blank"),
    INVALID_LOGIN_FORMAT("Login should have more than 5 chars"),

    LOGIN_EXIST("Login already exists"),

    USER_WITH_LOGIN_NOT_FOUND("User not found"),
    INVALID_PASS("Invalid password"),

    QUANTITY_LESS_OR_EQUAL_ZERO("Quantity has to be greater than 0"),
    INVALID_PRODUCT_NAME("Product name cannot be null and it has to consists of more than 5 chars"),
    PRICE_LESS_OR_EQUAL_ZERO("PRice has to be greater than 0"),

    PRODUCTS_OUT_OF_STOCK("Products out of stack")
    ;


    private final String defaultMessage;

    MiniShopErrorType(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
