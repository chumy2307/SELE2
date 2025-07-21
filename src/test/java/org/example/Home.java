package org.example;

import com.codeborne.selenide.Configuration;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class Home {

    @BeforeClass
    public void setUp() {
        // Set up the browser configuration using Selenide
        Configuration.browser = "chrome"; 
        Configuration.headless = false;
    }

    @Test
    public void testShopPage() {
        // Step 1: Navigate to the site
        open("https://demo.testarchitect.com/");

        // Step 2: Click on the "Shop" tab in the top bar
        $("#menu-item-5578").click(); 

        // Step 3: Verify the Shop page is displayed
        // Wait for a unique element to appear on the Shop page (e.g., Shop header)
        $("h1").shouldHave(text("Shop"));

        // Alternatively, check for the URL to confirm navigation
        String currentUrl = webdriver().driver().getCurrentFrameUrl();
        Assert.assertTrue(currentUrl.contains("/shop"), "Shop page URL is incorrect: " + currentUrl);
    }
}

