package com.auto.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import com.auto.model.Order;
import com.auto.model.Product;
import com.auto.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class OrderStatusPage extends CheckoutPage {

    String orderDetailItems = "ul.order_details > li.%s > strong";

    ElementsCollection orderItems = $$(By.cssSelector("table.order_details > tbody > tr.order_item"));

    SelenideElement oderDetailSubTotalPrice = $(By.xpath("//tr[th[contains(text(), 'Subtotal')]]/td/span"));
    SelenideElement oderDetailPaymentMethod = $(By.xpath("//tr[th[contains(text(), 'Payment method')]]/td"));
    SelenideElement oderDetailTotalPrice = $(By.xpath("//tr[th[contains(text(), 'Total')]]/td/span"));
    SelenideElement orderSuccessMsg = $(By.xpath("//div[@class='woocommerce-order']/p"));

    By oderDetailProductName = By.cssSelector("td.product-name > a");
    By oderDetailQuantity = By.cssSelector("td.product-name > strong");
    By oderDetailProductTotalPrice = By.cssSelector("td.product-total > span.amount");


    @Step("Get order success message")
    public String getOrderSuccessMsg() {
        return orderSuccessMsg.getText();
    }

    @Step("Get order overview details")
    public List<String> getOrderOverviewDetails(String... fields) {
        List<String> defaultFields = List.of("order", "date", "email", "total", "method");
        List<String> targetFields = (fields == null || fields.length == 0) ? defaultFields : List.of(fields);

        return targetFields.stream()
                .map(this::getOrderOverviewDetail)
                .collect(Collectors.toList());
    }

    @Step("Get basic order info that included order id, date and total price")
    public Order getBasicOrderInfo() {
        int totalQuantity = $$(oderDetailQuantity).texts()
                .stream().map(s -> s.replaceAll("[^0-9]", "").trim())
                .mapToInt(Integer::parseInt).sum();
        List<String> data = getOrderOverviewDetails("order", "date", "total");
        return Order.builder()
                .orderId("#" + data.get(0))
                .localDate(Utils.parseDate(data.get(1)))
                .totalDetail(String.format("%s FOR %d %s", data.get(2), totalQuantity, totalQuantity > 1 ? "ITEMS" : "ITEM"))
                .build();
    }

    @Step("Get order overview detail: {orderOverviewName}")
    public String getOrderOverviewDetail(String orderOverviewName) {
        return $(By.cssSelector(String.format(orderDetailItems, orderOverviewName))).getText();
    }

    @Step("Get order bank name")
    public String getOrderBankName() {
        return $(By.cssSelector(String.format(orderDetailItems, "bank_name"))).getText();
    }

    @Step("Get order bank account number")
    public String getOrderBankAccountNumber() {
        return $(By.cssSelector(String.format(orderDetailItems, "account_number"))).getText();
    }

    @Step("Get billing address")
    public List<String> getBillingAddress() {
        SelenideElement addressElement = $("section.woocommerce-customer-details address");
        List<String> texts = new ArrayList<>();
        String[] addressParts = addressElement.getText().split("\n");
        for (String part : addressParts) {
            if (!part.trim().isEmpty() && !texts.contains(part.trim())) {
                texts.add(part.trim());
            }
        }
        return texts;
    }

    @Step("Get products order details")
    public List<Product> getProductsOrderDetails() {
        List<Product> listProduct = new ArrayList<>();
        orderItems.forEach(orderItem -> {
            listProduct.add(new Product(
                    orderItem.find(oderDetailProductName).getText(),
                    Integer.parseInt(orderItem.find(oderDetailQuantity).getText().replaceAll("[^0-9]", "").trim()),
                    Utils.parseCurrencyToDouble(orderItem.find(oderDetailProductTotalPrice).getText())
            ));
        });

        return listProduct;
    }

    @Step("Get order detail sub total price")
    public String getOrderDetailSubTotalPrice() {
        return oderDetailSubTotalPrice.getText();
    }

    @Step("Get order detail total price")
    public String getOrderDetailTotalPrice() {
        return oderDetailTotalPrice.getText();
    }

    @Step("Get order detail payment method")
    public String getOrderDetailPaymentMethod() {
        return oderDetailPaymentMethod.getText();
    }

    @Step("Get non login order overview details")
    public List<String> getNonLoginOrderOverviewDetails() {
        return getOrderOverviewDetails("order", "date", "total", "method");
    }
}
