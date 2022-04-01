package com.mk.minishop.server.adapters.web;

import com.mk.minishop.api.products.NewProductForm;
import com.mk.minishop.api.products.ProductsDto;
import com.mk.minishop.server.products.ProductFacade;
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
    ProductsDto listAll() {
        return productFacade.listAll();
    }
}
