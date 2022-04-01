package com.mk.minishop.server.products;

import com.mk.minishop.api.orders.NewOrderFormDto;
import com.mk.minishop.api.orders.ProductOrderFormDto;
import com.mk.minishop.api.products.NewProductForm;
import com.mk.minishop.api.products.ProductsDto;
import com.mk.minishop.server.error.MiniShopError;
import io.vavr.collection.Map;
import io.vavr.control.Either;

import java.util.UUID;

public class ProductFacade {

    private final ProductRepository productRepository;

    public ProductFacade(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Either<MiniShopError, UUID> addProduct(NewProductForm form) {
        return Quantity.of(form.getQuantity())
                .flatMap(quantity -> Price.of(form.getPrice())
                        .flatMap(price -> ProductName.of(form.getName())
                                .map(productName -> createAndSave(quantity, productName, price))
                        )
                );
    }

    public ProductsDto listAll() {
        return new ProductsDto(productRepository.findAll()
                .map(Product::dto));
    }

    public boolean areThereInStack(NewOrderFormDto newOrderFormDto) {
        final Map<UUID, Integer> quantities = productRepository.findAll()
                .toMap(it -> it.dto().getId(), it -> it.dto().getQuantity());

        return newOrderFormDto.getProducts()
                .count(productOrder -> existAndQuantityIsEnough(productOrder, quantities)) == newOrderFormDto.getProducts().length();
    }

    private boolean existAndQuantityIsEnough(ProductOrderFormDto productOrderFormDto, Map<UUID, Integer> quantitiesMap) {
        return quantitiesMap.get(productOrderFormDto.getProductId())
                .map(quantity -> quantity >= productOrderFormDto.getQuantity())
                .getOrElse(false);
    }

    private UUID createAndSave(Quantity quantity, ProductName productName, Price price) {
        return productRepository.save(Product.create(quantity, productName, price))
                .dto()
                .getId();
    }
}
