package com.projects.StepDefinition;

import com.projects.pages.CartPage;
import com.projects.utility.DriverFactory;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

public class CartSteps {

    private WebDriver driver;
    private CartPage cartPage;

    private CartPage page() {
        if (cartPage == null) {
            driver = DriverFactory.getDriver();
            cartPage = new CartPage(driver);
        }
        return cartPage;
    }

    @When("I add an item to the cart")
    public void i_add_an_item_to_the_cart() {
        page().addItemToCart();
    }

    @Then("the cart badge should show {string}")
    public void the_cart_badge_should_show(String expectedCount) {
        String actual = page().getCartBadgeCount();
        Assertions.assertEquals(expectedCount, actual, "Cart badge count mismatch");
    }

    @When("I open the cart")
    public void i_open_the_cart() {
        page().goToCart();
    }

    @Then("I should see the item in the cart")
    public void i_should_see_the_item_in_the_cart() {
        Assertions.assertTrue(page().isItemInCart(), "Expected item to be present in cart");
    }

    @When("I remove the item from the cart")
    public void i_remove_the_item_from_the_cart() {
        page().removeItemFromCart();
    }

    @Then("I should not see the item in the cart")
    public void i_should_not_see_the_item_in_the_cart() {
        Assertions.assertFalse(page().isItemInCart(), "Expected item to be removed from cart");
    }

    @When("I remove the item from the products page")
    public void i_remove_the_item_from_the_products_page() {
        page().removeItemFromCart();
    }

    @Then("the cart badge should not be visible")
    public void the_cart_badge_should_not_be_visible() {
        Assertions.assertTrue(page().isCartBadgeGone(), "Expected cart badge to be gone");
    }
}
