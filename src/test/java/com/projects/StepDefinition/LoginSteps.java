package com.projects.StepDefinition;

import com.projects.pages.LoginPage;
import com.projects.utility.DriverFactory;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        driver = DriverFactory.getDriver();
        DriverFactory.openBaseUrl();
        loginPage = new LoginPage(driver);
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @Then("I should be redirected to the inventory page")
    public void i_should_be_redirected_to_the_inventory_page() {
        Assertions.assertTrue(driver.getCurrentUrl().contains("inventory.html"),
                "Expected to be on inventory page, but was: " + driver.getCurrentUrl());
    }

    @Then("I should see an error message containing {string}")
    public void i_should_see_an_error_message_containing(String expected) {
        String actual = loginPage.getErrorMessage();
        Assertions.assertTrue(actual != null && actual.toLowerCase().contains(expected.toLowerCase()),
                "Expected error to contain '" + expected + "' but was: " + actual);
    }
}
