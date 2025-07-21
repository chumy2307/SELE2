package com.auto.data.enums;

import lombok.Getter;

@Getter
public enum Departments {
    AUTOMOBILES_MOTORCYCLES("Automobiles & Motorcycles"),
    CAR_ELECTRONIC("Car Electronics"),
    MOBILE_PHONE_ACCESSORIES("Mobile Phone Accessories"),
    COMPUTER_OFFICE("Computer & Office"),
    TABLET_ACCESSORIES("Tablet Accessories"),
    CONSUMER_ELECTRONICS("Consumer Electronics"),
    ELECTRONIC_COMPONENTS_SUPPLIES("Electronic Components & Supplies"),
    PHONE_TELECOMMUNICATION("Phones & Telecommunications"),
    WATCHES("Watches"),
    ;

    private final String type;

    Departments(String type) {
        this.type = type;
    }
}

