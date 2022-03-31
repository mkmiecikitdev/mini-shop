package com.mk.minishop.adapters.web;

import com.mk.minishop.errors.RestErrorDto;
import io.vavr.Function1;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class RestErrorResolver {

    private final Map<com.mk.minishop.error.MiniShopErrorType, Function1<com.mk.minishop.error.MiniShopError, ResponseEntity<RestErrorDto>>> specificErrorsMappers;

    public RestErrorResolver() {
        this.specificErrorsMappers = HashMap.of(
                com.mk.minishop.error.MiniShopErrorType.INVALID_PASS, error -> buildSpecificResponseEntity(error, HttpStatus.UNAUTHORIZED),
                com.mk.minishop.error.MiniShopErrorType.USER_WITH_LOGIN_NOT_FOUND, error -> buildSpecificResponseEntity(error, HttpStatus.NOT_FOUND)
        );
    }

    public ResponseEntity<RestErrorDto> resolve(com.mk.minishop.error.MiniShopError error) {
        return specificErrorsMappers.get(error.getType())
                .fold(
                        () -> badRequest(error),
                        specificErrorMapper -> specificErrorMapper.apply(error)
                );
    }

    private ResponseEntity<RestErrorDto> badRequest(com.mk.minishop.error.MiniShopError error) {
        return ResponseEntity
                .badRequest()
                .body(restErrorDto(error));
    }

    private RestErrorDto restErrorDto(com.mk.minishop.error.MiniShopError error) {
        return new RestErrorDto(error.getType().name(), error.getMessage());
    }

    private ResponseEntity<RestErrorDto> buildSpecificResponseEntity(com.mk.minishop.error.MiniShopError minishopError, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus)
                .body(restErrorDto(minishopError));
    }
}
