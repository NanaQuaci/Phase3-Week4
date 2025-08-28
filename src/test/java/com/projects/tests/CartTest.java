package com.projects.tests;

import com.projects.base.BaseTest;
import com.projects.pages.CartPage;
import com.projects.pages.LoginPage;
import com.projects.testdata.LoginTestData;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;

import static com.projects.utility.DriverFactory.BASE_URL;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Swag Labs UI Tests")
@Feature("Cart Functionality")
public class CartTest extends BaseTest {

    @Test
    @Story("Add Item to Cart Only")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that an item can be added to the cart")
    public void testAddItemToCartOnly() {
        driver.get(BASE_URL);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(LoginTestData.VALID_USERNAME);
        loginPage.enterPassword(LoginTestData.VALID_PASSWORD);
        loginPage.clickLogin();

        CartPage cartPage = new CartPage(driver);
        cartPage.addItemToCart();

        // Verify item was added via the "Remove" button
        assertTrue(cartPage.isRemoveButtonVisible(), "Item should be added to cart");
    }

    @Test
    @Story("Add and Remove Item")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test adding and removing item from cart updates the cart contents")
    public void testAddAndRemoveItemFromCart() {
        driver.get(BASE_URL);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(LoginTestData.VALID_USERNAME);
        loginPage.enterPassword(LoginTestData.VALID_PASSWORD);
        loginPage.clickLogin();

        CartPage cartPage = new CartPage(driver);
        cartPage.addItemToCart();

        // Verify item added
        assertTrue(cartPage.isRemoveButtonVisible(), "Item should be added to cart");

        cartPage.goToCart();
        assertTrue(cartPage.isItemInCart(), "Item should be present in the cart");

        cartPage.removeItemFromCart();
        assertFalse(cartPage.isItemInCart(), "Item should be removed from the cart");
    }

    @Test
    @Story("Remove Item Without Navigating to Cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can remove item directly from product page after adding it")
    public void testRemoveItemWithoutOpeningCart() {
        driver.get(BASE_URL);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(LoginTestData.VALID_USERNAME);
        loginPage.enterPassword(LoginTestData.VALID_PASSWORD);
        loginPage.clickLogin();

        CartPage cartPage = new CartPage(driver);
        cartPage.addItemToCart();

        // Verify item added
        assertTrue(cartPage.isRemoveButtonVisible(), "Item should be added to cart");

        cartPage.removeItemFromCart();

        // Verify item removed
        assertFalse(cartPage.isRemoveButtonVisible(), "Item should be removed from cart");
    }


}
