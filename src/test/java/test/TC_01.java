package test;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.auto.data.enums.OrderProgress;
import com.auto.model.Product;
import com.auto.utils.Constants;
import com.auto.utils.Utils;
import org.testng.annotations.Test;

import java.util.List;

public class TC_01 extends TestBase {
    String checkoutPageName = "Checkout";
    String cartSubTotalPrice;
    String cartTotalPrice;
    String paymentMethod;
    String orderDate = Utils.getCurrentUtcTime();
    String orderTotalPrice;
    String orderSubTotalPrice;
    List<Product> randomProducts;
    List<Product> actualCartCheckoutProducts;
    List<Product> expectedCartCheckoutProducts;
    List<String> currentOrderOverviewDetails;
    List<String> expectedOrderOverviewDetails;
    List<String> actualBillingAddress;
    List<String> expectedBillingAddress;

    @Test(description = "Verify users can buy an item successfully")
    public void testUserCanBuyAnItemSuccessfully() {
        expectedBillingAddress = List.of(user.getFullName(), user.getAddress(), user.getCity(), user.getCountry(),
                user.getPhoneNumber(), user.getEmail());

        // 2. Login with valid credentials
        homePage.clickMyAccountLink();
        myAccountPage.login(user);
        myAccountPage.removeAllProduct();

        // 3. Navigate to All departments section
        // 4. Select Electronic Components & Supplies
        homePage.selectElectronicComponentsAndSuppliesDepartment();

        // 5. Verify the items should be displayed as a grid
        productsPage.switchViewToGrid();
        softAssert.assertTrue(productsPage.isGridView(), "The product items should be displayed as a grid.");

        // 6. Switch view to list
        productsPage.switchViewToList();

        // 7. Verify the items should be displayed as a list
        softAssert.assertTrue(productsPage.isListView(), "The product items should be displayed as a list.");

        // 8. Select any item randomly to purchase
        randomProducts = productsPage.getRandomProducts(1);

        // 9. Click 'Add to Cart'
        productsPage.addProductToCart(randomProducts);

        // 10. Go to the cart
        productDetailsPage.openCart();

        // 11. Verify item details in mini content
        softAssert.assertEquals(cartPage.getCartProductInfo(), randomProducts, "Products added to cart.");

        cartSubTotalPrice = cartPage.getSubTotalPrice();
        cartTotalPrice = cartPage.getTotalPrice();

        // 12. Click on Checkout
        cartPage.clickCheckoutButton();

        // 13. Verify checkout page displays
        softAssert.assertTrue(Selenide.title().contains(checkoutPageName), "The page title.");
        softAssert.assertTrue(WebDriverRunner.url().contains(checkoutPageName.toLowerCase()),
                "The page url should contain 'checkout'.");
        softAssert.assertEquals(checkoutPage.getCheckoutStatus(), checkoutPageName.toUpperCase(),
                "Current cart checkout navigation.");

        // 14. Verify item details in order
        paymentMethod = checkoutPage.getPaymentMethod();
        orderTotalPrice = checkoutPage.getOrderTotal();
        orderSubTotalPrice = checkoutPage.getOrderSubTotal();
        actualCartCheckoutProducts = checkoutPage.getCheckoutProductInfo();
        expectedCartCheckoutProducts = Product.toProductForCheckout(randomProducts);
        softAssert.assertEquals(actualCartCheckoutProducts, expectedCartCheckoutProducts, "Products on the checkout page.");
        softAssert.assertEquals(orderSubTotalPrice, cartSubTotalPrice, "Subtotal price on the checkout page.");
        softAssert.assertEquals(orderTotalPrice, cartTotalPrice, "Total price on the checkout page.");

        // 15. Fill the billing details with default payment method
        // 16. Click on PLACE ORDER
        checkoutPage.fillBillingDetailsAndPlaceOrder(user);

        // 16. Verify Order status page displays
        softAssert.assertEquals(orderStatusPage.getOrderSuccessMsg(), Constants.ORDER_SUCCESS_MSG,
                "The order success message.");
        softAssert.assertEquals(orderStatusPage.getCheckoutStatus(), OrderProgress.ORDER_STATUS.getType(),
                "Current cart checkout navigation.");

        // 17. Verify the Order details with billing and item information
        currentOrderOverviewDetails = orderStatusPage.getOrderOverviewDetails();
        expectedOrderOverviewDetails = List.of(paymentMethod, orderDate, user.getEmail(), orderTotalPrice);
        softAssert.assertTrue(currentOrderOverviewDetails.containsAll(expectedOrderOverviewDetails),
                "Order overview details.");

        softAssert.assertEquals(orderStatusPage.getOrderBankName(), Constants.DEFAULT_BANK_NAME,
                "Bank name.");
        softAssert.assertEquals(orderStatusPage.getOrderBankAccountNumber(), Constants.DEFAULT_BANK_ACCOUNT_NUMBER,
                "Bank account number.");

        actualBillingAddress = orderStatusPage.getBillingAddress();
        softAssert.assertEquals(actualBillingAddress, expectedBillingAddress,
                "Billing address.");

        softAssert.assertEquals(orderStatusPage.getProductsOrderDetails(), expectedCartCheckoutProducts,
                "Products on the checkout page.");

        softAssert.assertEquals(orderStatusPage.getOrderDetailSubTotalPrice(), orderSubTotalPrice,
                "Order detail subtotal price.");
        softAssert.assertEquals(orderStatusPage.getOrderDetailTotalPrice(), orderTotalPrice,
                "Order detail total price.");
        softAssert.assertEquals(orderStatusPage.getOrderDetailPaymentMethod(), paymentMethod,
                "Order detail payment method");

        softAssert.assertAll();
    }
}

