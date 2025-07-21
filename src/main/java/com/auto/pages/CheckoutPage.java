package com.auto.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import com.auto.data.enums.PaymentMethods;
import com.auto.model.Product;
import com.auto.model.User;
import com.auto.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CheckoutPage extends CartPage {

    SelenideElement billingFirstNameTextbox = $(By.id("billing_first_name"));
    SelenideElement billingLastNameTextbox = $(By.id("billing_last_name"));
    SelenideElement billingCountrySelection = $(By.id("select2-billing_country-container"));
    SelenideElement billingCountryFindTextbox = $(By.xpath("//input[@aria-owns='select2-billing_country-results']"));
    SelenideElement billingCountryResult = $(By.cssSelector("ul#select2-billing_country-results > li"));
    SelenideElement billingAddressTextbox = $(By.id("billing_address_1"));
    SelenideElement billingCitySelection = $(By.id("billing_city"));
    SelenideElement billingPhoneTextbox = $(By.id("billing_phone"));
    SelenideElement billingEmailTextbox = $(By.id("billing_email"));
    SelenideElement checkoutTotalAmount = $(By.cssSelector("tr.order-total span.amount"));
    SelenideElement checkoutSubTotalAmount = $(By.cssSelector("tr.cart-subtotal span.amount"));
    SelenideElement placeOrderButton = $(By.id("place_order"));
    SelenideElement paymentMethod = $("input[name='payment_method']:checked ~ label");
    SelenideElement errorField = $("ul.woocommerce-error");

    ElementsCollection checkoutStatus = $$(By.cssSelector("div.cart-checkout-nav > a.active"));
    ElementsCollection orderItems = $$("tr.cart_item");
    ElementsCollection inputMandatoryFieldName = $$("p.validate-required:has(span > input) > label");
    ElementsCollection errorFieldNames = $$("ul.woocommerce-error > li");

    By productNameInOrder = By.xpath(".//td[@class='product-name']");
    By productQuantityInOrder = By.xpath(".//td[@class='product-name']/strong[@class='product-quantity']");
    By productTotalInOrder = By.xpath(".//td[@class='product-total']");


    @Step("Get checkout status in navigation")
    public String getCheckoutStatus() {
        return checkoutStatus.get(checkoutStatus.size() - 1).getText().trim();
    }

    @Step("Get checkout products info")
    public List<Product> getCheckoutProductInfo() {
        List<Product> productList = new ArrayList<>();

        orderItems.forEach(orderItem -> {
            productList.add(new Product(
                    orderItem.find(productNameInOrder).getText().replaceAll("\\s*×\\s*\\d+", ""),
                    Integer.parseInt(orderItem.find(productQuantityInOrder).getText().replaceAll("[^0-9]", "").trim()),
                    Utils.parseCurrencyToDouble(orderItem.find(productTotalInOrder).getText())
            ));
        });

        return productList;
    }

    @Step("Fill billing details")
    public void fillBillingDetails(User user) {
        billingFirstNameTextbox.setValue(user.getFirstName());
        billingLastNameTextbox.setValue(user.getLastName());
        selectCountry(user);
        billingAddressTextbox.setValue(user.getAddress());
        billingCitySelection.setValue(user.getCity());
        billingPhoneTextbox.setValue(user.getPhoneNumber());
        billingEmailTextbox.setValue(user.getEmail());
    }

    private void selectCountry(User user) {
        billingCountrySelection.click();
        billingCountryFindTextbox.setValue(user.getCountry());
        billingCountryResult.click();
    }

    @Step("Click 'Place Order' button")
    public void clickPlaceOrderButton() {
        placeOrderButton.click();
        waitForLoadingComplete();
    }

    public void fillBillingDetailsAndPlaceOrder(User user) {
        fillBillingDetails(user);
        clickPlaceOrderButton();
        waitForLoadingComplete();
    }

    @Step("Get payment method")
    public String getPaymentMethod() {
        return paymentMethod.getText();
    }

    @Step("Select payment method")
    public void selectPaymentMethod(PaymentMethods paymentMethod) {
        $(By.id(paymentMethod.getId())).click();
    }

    @Step("Get order total amount")
    public String getOrderTotal() {
        return checkoutTotalAmount.getText();
    }

    @Step("Get order subtotal amount")
    public String getOrderSubTotal() {
        return checkoutSubTotalAmount.getText();
    }

    @Step("Get input mandatory field name")
    public List<String> getInputMandatoryFieldName() {
        return inputMandatoryFieldName.stream().map(label -> label.text().replaceAll("\\*", "").trim()).toList();
    }

    @Step("Get error field name")
    public List<String> getErrorFieldNames() {
        errorField.shouldBe(visible);
        return errorFieldNames.stream().map(label -> label.text()
                .replaceAll("Billing (.+?) is a required field\\.", "$1").trim()).toList();
    }
}

