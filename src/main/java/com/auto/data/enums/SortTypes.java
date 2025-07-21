package com.auto.data.enums;

import lombok.Getter;

@Getter
public enum SortTypes {
    DEFAULT_SORTING("Default sorting", "menu_order"),
    SORT_BY_POPULARITY("Sort by popularity", "popularity"),
    SORT_BY_AVERAGE_RATING("Sort by average rating", "rating"),
    SORT_BY_LATEST("Sort by latest", "date"),
    SORT_BY_PRICE_LOW_TO_HIGH("Sort by price: low to high", "price"),
    SORT_BY_PRICE_HIGH_TO_LOW("Sort by price: high to low", "price-desc"),
    ;

    private final String label;
    private final String value;

    SortTypes(String label, String value) {
        this.label = label;
        this.value = value;
    }
}