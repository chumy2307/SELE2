package com.auto.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import com.auto.config.EnvConfig;
import com.auto.data.enums.NavItems;
import com.auto.model.Order;
import com.auto.model.User;
import com.auto.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Selenide.$;

public class MyAccountPage extends BasePage {

    private final SelenideElement usernameTextbox = $(By.id("username"));
    private final SelenideElement passwordTextbox = $(By.id("password"));
    private final SelenideElement registerEmailTextbox = $(By.id("reg_email"));
    private final SelenideElement loginButton = $(By.name("login"));
    private final SelenideElement registerButton = $(By.name("register"));
    private final SelenideElement myAccountErrorMsg = $(By.cssSelector("ul.woocommerce-error"));
    private final String myAccountNavigationPath = "//nav/ul/li/a[text()='%s']";
    private final String orderRowPath = "//tr[.//td[@data-title='Order']//a[contains(text(), '%s')]]";


    @Step("Register new account")
    public void registerAccount(User user) {
        registerEmailTextbox.setValue(user.getEmail());
        registerButton.click();
    }

    @Step("Login")
    public void login(User user) {
        if (user.getEmail().equals(EnvConfig.getEmail())) {
            usernameTextbox.setValue(user.getEmail());
            passwordTextbox.setValue(user.getPassword());
            loginButton.click();
        } else {
            registerAccount(user);
        }
        myAccountErrorMsg.shouldBe(hidden);
    }

    private void clickMyAccountNavigation(NavItems myAccountPageNav) {
        $(By.xpath(String.format(myAccountNavigationPath, myAccountPageNav.getItemName()))).click();
    }

    @Step("Click 'Orders' navigation")
    public void clickOrdersNavigation() {
        clickMyAccountNavigation(NavItems.ORDERS);
    }

    @Step("Get order details by order ids")
    public List<Order> getOrderDetailsByOrderIds(List<String> orderIds) {
        return orderIds.stream().map(orderId -> {
            SelenideElement row = $(By.xpath(String.format(orderRowPath, orderId)));
            return Order.builder()
                    .orderId(row.$("td[data-title='Order']").text().trim())
                    .localDate(Utils.parseDate(row.$("td[data-title='Date']").text().trim()))
                    .totalDetail(row.$("td[data-title='Total']").text().trim())
                    .build();
        }).collect(Collectors.toList());
    }
}
