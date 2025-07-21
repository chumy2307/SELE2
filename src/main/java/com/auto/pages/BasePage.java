package com.auto.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import com.auto.data.enums.Departments;
import com.auto.data.enums.NavItems;
import com.auto.utils.Constants;
import com.auto.utils.Utils;

import java.time.Duration;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Slf4j
public class BasePage {

    protected final SelenideElement addCartSuccessNotify = $(By.cssSelector("div.et-notify"));
    private final String navItems = "//ul[@id = 'menu-main-menu-1']/li[a[text() = '%s']]";
    private final String departmentsItems = "//ul[@id = 'menu-all-departments-1']/li[a[text() = '%s']]";
    private final String webHeader = "//div[@class = 'header-wrapper']";
    private final SelenideElement allDepartmentsButton = $(By.xpath("//div[@class='secondary-title']"));
    private final SelenideElement cardButton = $(By.xpath(webHeader)).find(By.xpath(".//div[contains(@class, 'header-cart')]"));
    protected final SelenideElement cardCounter = cardButton.find(By.xpath(".//a//span[contains(@class, 'quantity')]"));
    private final SelenideElement myAccountLink = $(By.xpath(webHeader)).find(By.xpath(".//a[contains(@href, 'my-account')]"));
    protected final SelenideElement removeProductButton = $(By.xpath("//a[text()='Remove']"));
    private final SelenideElement scrollToTop = $(By.id("back-top"));
    private final SelenideElement registerNewsletterPopup = $("div#popmake-5701");
    private final SelenideElement loadingSpinner = $("div.et-loader.product-ajax > svg > circle");


    public void removeAllProduct() {
        int productNumberInCart = Integer.parseInt(cardCounter.getText());
        if (productNumberInCart > 0) {
            openCart();
            for (int i = 0; i < productNumberInCart; i++) {
                removeProductButton.shouldBe(visible);
                removeProductButton.click();
            }
        }
    }

    @Step("Click 'My Account' link")
    public void clickMyAccountLink() {
        myAccountLink.click();
    }

    public void clickNavItem(NavItems navItem) {
        $(By.xpath(String.format(navItems, navItem.getItemName()))).scrollIntoCenter().click();
    }

    public void selectDepartment(Departments department) {
        Utils.scrollToTop();
        allDepartmentsButton.hover();
        $(By.xpath(String.format(departmentsItems, department.getType()))).click();
    }

    @Step("Open cart")
    public void openCart() {
        cardButton.scrollIntoCenter().click();
    }

    private void clickScrollToTop() {
        scrollToTop.click();
        scrollToTop.shouldBe(hidden);
    }

    protected void waitForLoadingComplete() {
        loadingSpinner.shouldBe(hidden, Duration.ofMillis(Constants.TIMEOUT_IN_MILLISECONDS));
    }

    public void closeRegisterNewsletterPopup() {
        registerNewsletterPopup.shouldBe(visible);
        registerNewsletterPopup.find(By.cssSelector("button")).click();
        registerNewsletterPopup.shouldBe(hidden);
    }

    @Step("Select department: Electronic Components & Supplies")
    public void selectElectronicComponentsAndSuppliesDepartment() {
        selectDepartment(Departments.ELECTRONIC_COMPONENTS_SUPPLIES);
    }

    @Step("Go to Shop page")
    public void goToShopPage() {
        clickNavItem(NavItems.SHOP);
        closeRegisterNewsletterPopup();
    }
}

