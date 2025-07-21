package com.auto.data.enums;

import lombok.Getter;

@Getter
public enum PaymentMethods {
    DIRECT_BANK_TRANSFER("Direct bank transfer", "payment_method_bacs"),
    CHECK_PAYMENTS("Check payments", "payment_method_cheque"),
    CASH_ON_DELIVERY("Cash on delivery", "payment_method_cod"),
    ;

    private final String name;
    private final String id;

    PaymentMethods(String name, String id) {
        this.name = name;
        this.id = id;
    }
}
