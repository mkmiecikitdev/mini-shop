package com.mk.minishop.client.products;

import com.mk.minishop.api.products.NewProductForm;
import com.mk.minishop.api.products.ProductsDto;
import com.mk.minishop.client.AbstractClient;
import com.mk.minishop.client.error.RestRequestError;
import io.vavr.control.Either;


public class ProductsClient extends AbstractClient {
    public static final String PRODUCTS = "/products";

    public ProductsClient(String host, int port) {
        super(host, port);
    }

    public Either<RestRequestError, ProductsDto> listAll(String token) {
        return getAuthorized(PRODUCTS, token, ProductsDto.class);
    }

    public Either<RestRequestError, ProductsDto> add(NewProductForm form, String token) {
        return postAuthorized(PRODUCTS, token, form, ProductsDto.class);
    }
}
