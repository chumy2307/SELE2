package com.auto.data.enums;

import lombok.Getter;

@Getter
public enum OrderProgress {
    SHOPPING_CART("SHOPPING CART"),
    CHECKOUT("CHECKOUT"),
    ORDER_STATUS("ORDER STATUS");

    private final String type;

    OrderProgress(String type) {
        this.type = type;
    }
}
