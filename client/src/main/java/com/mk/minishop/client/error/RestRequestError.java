package com.mk.minishop.client.error;

import io.vavr.control.Either;

public class RestRequestError {

    public static RestRequestError CANNOT_WRITE_BODY = new RestRequestError("CANNOT_WRITE_BODY", "Cannot write body", 500);
    public static RestRequestError UNKNOWN_ERROR = new RestRequestError("UNKNOWN_ERROR", "Unknown error", 500);
    public static RestRequestError CANNOT_PARSE_SUCCESS_RESPONSE = new RestRequestError("CANNOT_PARSE_SUCCESS_RESPONSE", "Cannot parse success response", 500);
    public static RestRequestError CANNOT_PARSE_ERROR_RESPONSE = new RestRequestError("CANNOT_PARSE_ERROR_RESPONSE", "Cannot parse error response", 500);

    private final String code;
    private final String message;
    private final int httpStatus;

    public RestRequestError(String code, String message, int httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public <R> Either<RestRequestError, R> toEither() {
        return Either.left(this);
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getHttpStatus() {
        return httpStatus;
    }
}
