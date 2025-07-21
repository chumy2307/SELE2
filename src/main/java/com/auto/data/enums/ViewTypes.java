package com.auto.data.enums;

import lombok.Getter;

@Getter
public enum ViewTypes {
    LIST("list"),
    GRID("grid");

    private final String type;

    ViewTypes(String type) {
        this.type = type;
    }
}

