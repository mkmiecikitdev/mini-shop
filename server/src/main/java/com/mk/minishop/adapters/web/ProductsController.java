package com.mk.minishop.adapters.web;

import com.mk.minishop.products.NewProductForm;
import com.mk.minishop.products.ProductDto;
import com.mk.minishop.products.ProductFacade;
import io.vavr.collection.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ProductsController {

    private final ProductFacade productFacade;
    private final RestErrorResolver restErrorResolver = new RestErrorResolver();

    ProductsController(ProductFacade productFacade) {
        this.productFacade = productFacade;
    }

    @PostMapping("/products")
    ResponseEntity<?> addProduct(@RequestBody NewProductForm form) {
        return productFacade.addProduct(form)
                .map(it -> productFacade.listAll())
                .fold(
                        restErrorResolver::resolve,
                        ResponseEntity::ok
                );
    }

    @GetMapping("/products")
    Set<ProductDto> listAll() {
        return productFacade.listAll();
    }
}
