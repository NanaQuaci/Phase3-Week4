package com.projects.StepDefinition;

import com.projects.pages.CheckoutPage;
import com.projects.utility.DriverFactory;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

public class CheckoutSteps{

    private WebDriver driver;
    private CheckoutPage checkoutPage;

    private CheckoutPage page() {
        if (checkoutPage == null) {
            driver = DriverFactory.getDriver();
            checkoutPage = new CheckoutPage(driver);
        }
        return checkoutPage;
    }

    // Actions
    @When("I proceed to checkout")
    public void i_proceed_to_checkout() {
        page().clickCheckout();
    }

    @When("I enter {string} as First Name")
    public void i_enter_first_name(String firstName) {
        page().enterFirstName(firstName);
    }

    @When("I enter {string} as Last Name")
    public void i_enter_last_name(String lastName) {
        page().enterLastName(lastName);
    }

    @When("I enter {string} as Postal Code")
    public void i_enter_postal_code(String postalCode) {
        page().enterPostalCode(postalCode);
    }

    @When("I continue checkout")
    public void i_continue_checkout() {
        page().clickContinue();
    }

    @When("I finish checkout")
    public void i_finish_checkout() {
        page().clickFinish();
    }

    // Validations
    @Then("I should be on the overview page")
    public void i_should_be_on_the_overview_page() {
        Assertions.assertTrue(page().isOnOverviewPage());
    }

    @Then("I should see the order confirmation")
    public void i_should_see_the_order_confirmation() {
        Assertions.assertTrue(page().isOrderComplete());
    }

    @Then("I should see checkout error {string}")
    public void i_should_see_checkout_error(String expectedError) {
        Assertions.assertEquals(expectedError, page().getErrorMessage());
    }

    @Then("I should see error {string}")
    public void i_should_see_error(String expectedError) {
        Assertions.assertEquals(expectedError, page().getErrorMessage());
    }

    @Then("I should see the order confirmation message")
    public void i_should_see_order_confirmation_message() {
        Assertions.assertTrue(page().isOrderComplete());
    }

    // Navigation
    @Given("I navigate to the checkout page")
    public void i_navigate_to_the_checkout_page() {
        page().navigateToCheckoutPage();
    }

    @Given("I navigate directly to the checkout page")
    public void i_navigate_directly_to_checkout_page() {
        driver = DriverFactory.getDriver();
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.navigateToCheckoutPage();
        checkoutPage.waitForCheckoutPageToLoad();
    }
}
