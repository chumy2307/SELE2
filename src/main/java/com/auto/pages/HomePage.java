package com.auto.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class HomePage extends BasePage {

    private SelenideElement closeCookieButton = $(By.id("cn-close-notice"));
    private SelenideElement cookieDialog = $(By.id("cookie-notice"));


    public void closeCookie() {
        if (cookieDialog.isDisplayed()) {
            closeCookieButton.click();
        }
    }
}
