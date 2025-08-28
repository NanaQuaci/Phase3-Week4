package com.projects.StepDefinition;

import com.projects.pages.CartPage;
import com.projects.utility.DriverFactory;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Given("I have added an item to the cart")
    public void i_have_added_an_item_to_the_cart() {
        page().addItemToCart();
    }

    @When("I add an item to the cart")
    public void i_add_an_item_to_the_cart() {
        page().addItemToCart();
    }

    @Then("the item should be added to the cart")
    public void the_item_should_be_added_to_the_cart() {
        assertTrue(page().isItemAdded(), "Expected item to be added to cart");
    }


    @When("I open the cart")
    public void i_open_the_cart() {
        page().goToCart();
        // Wait for cart items to appear
        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(5));
        wait.until(driver -> !driver.findElements(page().cartItem).isEmpty());
    }


    @Then("I should see the item in the cart")
    public void i_should_see_the_item_in_the_cart() {
        assertTrue(page().isItemInCart(), "Expected item to be present in cart");
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
}