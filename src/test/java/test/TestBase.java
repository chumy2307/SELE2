package test;

import com.codeborne.selenide.Selenide;
import com.auto.config.EnvConfig;
import com.auto.model.User;
import com.auto.pages.*;
import com.auto.report.TestListener;
import com.auto.utils.Constants;
import com.auto.utils.CustomSoftAssert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import static com.codeborne.selenide.Selenide.open;

@Listeners(TestListener.class)
public class TestBase {
    HomePage homePage = new HomePage();
    MyAccountPage myAccountPage = new MyAccountPage();
    CartPage cartPage = new CartPage();
    CheckoutPage checkoutPage = new CheckoutPage();
    OrderStatusPage orderStatusPage = new OrderStatusPage();
    ProductDetailsPage productDetailsPage = new ProductDetailsPage();
    ProductsPage productsPage = new ProductsPage();

    User user;
    CustomSoftAssert softAssert;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        EnvConfig.ChromeConfig(); // TODO: apply cross browser

        // 1. Open browser and go to https://demo.testarchitect.com/
        open(Constants.BASE_URL);
        homePage.closeCookie();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        softAssert = new CustomSoftAssert();
        user = User.defaultUser();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        Selenide.clearBrowserCookies();
        Selenide.closeWebDriver();
    }
}