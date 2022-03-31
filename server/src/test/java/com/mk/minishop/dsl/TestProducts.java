package com.mk.minishop.dsl;

import com.mk.minishop.products.NewProductForm;

import java.math.BigDecimal;

public class TestProducts {

    public static final NewProductForm BALLS_WITH_QUANTITY_5 = new NewProductForm("Ball", 5, new BigDecimal("10"));
    public static final NewProductForm SHOES_WITH_QUANTITY_2 = new NewProductForm("Shoes", 1, new BigDecimal("15"));

}
