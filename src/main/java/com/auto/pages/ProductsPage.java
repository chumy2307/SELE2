package com.auto.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import com.auto.data.enums.SortTypes;
import com.auto.data.enums.ViewTypes;
import com.auto.model.Product;
import com.auto.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Slf4j
public class ProductsPage extends BasePage {

    private final String switchViewButtonPath = "//div[contains(@class, 'switch-%s ')]";
    private final String productItemsPath = "div.row.products.products-loop";
    private final String productNamePath = "//h2[a[text()='%s']]";
    private final String productPrefix = productNamePath + "/following-sibling::";
    private final String addToCartButton = productPrefix + "a[text()='Add to cart']";
    private final ElementsCollection productNames = $$(By.cssSelector("div.content-product  h2"));
    private final ElementsCollection productPrices = $$(By.xpath(productPricesText("//h2/following-sibling::")));
    private final SelenideElement orderByDropdown = $(By.name("orderby"));

    private String productPricesText(String productPrefix) {
        return productPrefix + "span[@class='price']//ins//bdi | " + productPrefix + "span[@class='price'][not(.//ins)]//bdi";
    }

    private SelenideElement productPrice(String productName) {
        return $(By.xpath(String.format(productPricesText(productPrefix), productName, productName)));
    }


    private void switchView(ViewTypes viewType) {
        String viewName = viewType.getType();
        SelenideElement switchViewButton = $(By.xpath(String.format(switchViewButtonPath, viewName)));

        if (!switchViewButton.shouldBe(visible).getAttribute("class").contains("switcher-active")) {
            switchViewButton.click();
            switchViewButton.shouldHave(cssClass("switcher-active"));
        }
    }

    private List<Double> getProductListPrice() {
        return productPrices.stream()
                .map(SelenideElement::getText)
                .map(Utils::parseCurrencyToDouble)
                .collect(Collectors.toList());
    }

    private boolean isPriceOrderCorrect(List<Double> prices, boolean isDesc) {
        List<Double> expectedPrices = new ArrayList<>(prices);
        expectedPrices.sort(isDesc ? Comparator.reverseOrder() : Comparator.naturalOrder());
        return prices.equals(expectedPrices);
    }

    private Product getProductDetail(int index) {
        String productName = productNames.get(index).getText().trim();
        String convertName = Utils.formatProductName(productName);
        String productPriceText = productPrice(convertName).getText().trim();

        return new Product(
                productName,
                1,
                Utils.parseCurrencyToDouble(productPriceText),
                Utils.parseCurrencyToDouble(productPriceText) * 1
        );
    }

    private boolean isProductItemsViewMode(String viewMode) {
        try {
            $(By.cssSelector(productItemsPath + ".products-" + viewMode)).should(visible);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void sortProducts(SortTypes sortType) {
        orderByDropdown.selectOption(sortType.getLabel());
        waitForLoadingComplete();
    }

    @Step("Get products randomly")
    public List<Product> getRandomProducts(int numberOfProducts) {
        if (productNames.isEmpty() || numberOfProducts > productNames.size()) {
            throw new IllegalArgumentException(productNames.isEmpty()
                    ? "No products found on the page."
                    : "The number of products requested exceeds the number of products available.");
        }

        List<Integer> indexes = IntStream.range(0, productNames.size()).boxed().collect(Collectors.toList());
        Collections.shuffle(indexes);

        return IntStream.range(0, numberOfProducts)
                .mapToObj(i -> getProductDetail(indexes.get(i)))
                .collect(Collectors.toList());
    }

    @Step("Add product to cart")
    public void addProductToCart(List<Product> products) {
        products.forEach(product -> {
            String productName = product.getProductName();
            $(By.xpath(String.format(addToCartButton, Utils.formatProductName(productName))))
                    .shouldBe(visible)
                    .scrollIntoCenter()
                    .click();
            log.info("Added to cart product: {}", productName);
        });

        addCartSuccessNotify.shouldBe(visible);
        cardCounter.shouldHave(exactText(String.valueOf(products.size())));
    }

    @Step("Check product items view mode is grid")
    public boolean isGridView() {
        return isProductItemsViewMode(ViewTypes.GRID.getType());
    }

    @Step("Check product items view mode is list")
    public boolean isListView() {
        return isProductItemsViewMode(ViewTypes.LIST.getType());
    }


    @Step("Switch view to grid")
    public void switchViewToGrid() {
        switchView(ViewTypes.GRID);
    }

    @Step("Switch view to list")
    public void switchViewToList() {
        switchView(ViewTypes.LIST);
    }




}
