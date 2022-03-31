package com.mk.minishop.adapters.web;

import com.mk.minishop.auth.UserContextProvider;
import com.mk.minishop.orders.NewOrderFormDto;
import com.mk.minishop.orders.OrdersFacade;
import io.vavr.collection.HashMap;
import io.vavr.collection.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class OrdersController {

    private final OrdersFacade ordersFacade;
    private final UserContextProvider userContextProvider;
    private final RestErrorResolver restErrorResolver = new RestErrorResolver();

    OrdersController(OrdersFacade ordersFacade, UserContextProvider userContextProvider) {
        this.ordersFacade = ordersFacade;
        this.userContextProvider = userContextProvider;
    }

    @PostMapping("/orders")
    ResponseEntity<?> addOrder(@RequestBody NewOrderFormDto newOrderFormDto) {
        return ordersFacade.addOrder(newOrderFormDto, userContextProvider.userContext().getId())
                .fold(
                        restErrorResolver::resolve,
                        ResponseEntity::ok
                );
    }

    @GetMapping("/orders/count")
    Map<String, Long> count() {
        return HashMap.of("count", ordersFacade.countByUserId(userContextProvider.userContext().getId()));
    }
}
