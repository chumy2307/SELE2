package com.auto.pages;

import com.auto.data.enums.QuantityAction;
import com.auto.model.Product;
import com.auto.utils.Constants;
import com.auto.utils.Utils;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;


import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CartPage extends BasePage {

    private final By productNameValue = By.className("product-title");
    private final By productPriceValue = By.cssSelector("td.product-price");
    private final By productQuantityValue = By.cssSelector("div.quantity > input");
    private final By minusQuantityButton = By.cssSelector("div.quantity > span.minus");
    private final By plusQuantityButton = By.cssSelector("div.quantity > span.plus");
    private final By productSubTotalValue = By.cssSelector("td.product-subtotal");

    private final SelenideElement updateCartButton = $(By.name("update_cart"));
    private final SelenideElement calculationSpinner = $("div.blockUI.blockOverlay");
    private final SelenideElement checkoutButton = $("div.wc-proceed-to-checkout");
    private final SelenideElement subTotalPrice = $("div.cart_totals tr.cart-subtotal bdi");
    private final SelenideElement totalPrice = $("div.cart_totals tr.order-total bdi");
    private final SelenideElement clearShoppingCartButton = $("a.clear-cart");
    private final SelenideElement emptyMsg = $("div.cart-empty > h1");

    private final ElementsCollection products = $$(By.xpath("//tr[contains(@class, 'cart_item')][.//a[@class='product-title']]"));


    @Step("Get cart product info")
    public List<Product> getCartProductInfo() {
        return products.stream().map(product -> new Product(
                product.find(productNameValue).getText().trim(),
                Integer.parseInt(product.find(productQuantityValue).getValue()),
                Utils.parseCurrencyToDouble(product.find(productPriceValue).getText()),
                Utils.parseCurrencyToDouble(product.find(productSubTotalValue).getText())
        )).collect(Collectors.toList());
    }

    @Step("Click 'Checkout' button")
    public void clickCheckoutButton() {
        checkoutButton.click();
    }

    public String getSubTotalPrice() {
        return subTotalPrice.getText();
    }

    public String getTotalPrice() {
        return totalPrice.getText();
    }

    private SelenideElement findProductElementByName(String productName) {
        return products.stream()
                .filter(product ->
                        product.find(this.productNameValue)
                                .getText()
                                .trim()
                                .equals(productName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with name '" + productName + "' not found"));
    }

    public int getCurrentQuantity(String productName) {
        return Integer.parseInt(findProductElementByName(productName).find(productQuantityValue).getValue());
    }

    public String getCurrentSubTotal(String productName) {
        return findProductElementByName(productName).find(productSubTotalValue).text();
    }

    protected void waitForCalculationComplete() {
        calculationSpinner.shouldBe(visible).shouldBe(hidden, Duration.ofMillis(Constants.TIMEOUT_IN_MILLISECONDS));
    }

    private void changeQuantityViaButton(String productName, int clickCount, QuantityAction action) {
        int actualClicks;
        By buttonElement;

        if (action.equals(QuantityAction.INCREASE)) {
            actualClicks = clickCount;
            buttonElement = plusQuantityButton;
        } else {
            actualClicks = Math.min(clickCount, getCurrentQuantity(productName));
            buttonElement = minusQuantityButton;
        }

        SelenideElement actionButton = findProductElementByName(productName).find(buttonElement);

        for (int i = 0; i < actualClicks; i++) {
            actionButton.click();
        }
    }

    @Step("Click on Plus(+) button")
    public void clickPlusButton(String productName) {
        changeQuantityViaButton(productName, 1, QuantityAction.INCREASE);
        waitForCalculationComplete();
    }

    @Step("Click on Minus(-) button")
    public void clickMinusButton(String productName) {
        changeQuantityViaButton(productName, 1, QuantityAction.DECREASE);
        waitForCalculationComplete();
    }

    @Step("Click 'Update cart' button")
    public void clickUpdateCartButton() {
        updateCartButton.click();
    }

    @Step("Change quantity and click 'Update cart' button")
    public void changeQuantityAndUpdateCart(String productName, int correctlyQuantity) {
        findProductElementByName(productName).find(productQuantityValue).setValue(correctlyQuantity + "");
        clickUpdateCartButton();
        waitForCalculationComplete();
    }


    }


