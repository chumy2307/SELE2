package com.auto.pages;


import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;




import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;


public class ProductDetailsPage extends BasePage {



    private final SelenideElement addToCardButton = $(By.xpath("//button[@type='submit' and text()='Add to cart']"));



    @Step("Click 'Add to Cart' button")
    public void clickAddToCardButton() {
        addToCardButton.click();
        addCartSuccessNotify.shouldBe(visible);
    }


}
