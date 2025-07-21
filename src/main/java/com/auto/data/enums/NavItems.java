package com.auto.data.enums;

import lombok.Getter;

@Getter
public enum NavItems {
    HOME("Home"),
    ABOUT_US("About Us"),
    SHOP("Shop"),
    OFFERS("Offers"),
    BLOG("Blog"),
    CONTACT_US("Contact Us"),
    DASHBOARD("Dashboard"),
    ORDERS("Orders"),
    SUBSCRIPTIONS("Subscriptions"),
    DOWNLOADS("Downloads"),
    ADDRESS("Addresses"),
    ACCOUNT_DETAILS("Account details"),
    LOGOUT("Logout"),
    ;

    private final String itemName;

    NavItems(String itemName) {
        this.itemName = itemName;
    }
}
