package com.mk.minishop.test.products;

import com.mk.minishop.api.products.ProductsDto;
import com.mk.minishop.client.error.RestRequestError;
import com.mk.minishop.test.BaseIT;
import com.mk.minishop.test.dsl.ClientEitherAssertion;
import com.mk.minishop.test.dsl.TestProducts;
import io.vavr.control.Either;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ProductsClientTest extends BaseIT {

    @Test
    public void shouldBeAbleToAddProductAsAdmin() {
        // given
        final String token = testTokenCreator.generateForAdmin(UUID.randomUUID());

        // when
        final Either<RestRequestError, ProductsDto> result = productsClient.add(TestProducts.NOTEBOOKS_QUANTITY_3, token);

        new ClientEitherAssertion<>(result)
                .isOk()
                .andAssertThat(products -> Assertions.assertThat(products.getProducts()).hasSize(1));
    }

    @Test
    public void shouldNotBeAbleToAddProductAsUser() {
        // given
        final String token = testTokenCreator.generateForUser(UUID.randomUUID());

        // when
        final Either<RestRequestError, ProductsDto> result = productsClient.add(TestProducts.NOTEBOOKS_QUANTITY_3, token);

        new ClientEitherAssertion<>(result)
                .isError()
                .withErrorCode("ACCESS_DENIED")
                .withErrorMessage("User can not access this resource")
                .withErrorHttpStatus(403);
    }

    @Test
    public void shouldNotBeAbleToAddProductWithInvalidToken() {
        // given
        final String token = testTokenCreator.generateInvalid();

        // when
        final Either<RestRequestError, ProductsDto> result = productsClient.add(TestProducts.NOTEBOOKS_QUANTITY_3, token);

        new ClientEitherAssertion<>(result)
                .isError()
                .withErrorCode("ACCESS_DENIED")
                .withErrorMessage("User can not access this resource")
                .withErrorHttpStatus(403);
    }

}
