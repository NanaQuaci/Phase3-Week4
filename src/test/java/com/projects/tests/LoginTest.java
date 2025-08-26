package com.projects.tests;

import com.projects.base.BaseTest;
import com.projects.pages.LoginPage;
import com.projects.testdata.LoginTestData;
import io.qameta.allure.*;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static com.projects.utility.DriverFactory.BASE_URL;
import static org.junit.jupiter.api.Assertions.*;

@Epic("Swag Labs UI Tests")
@Feature("Login Functionality")
public class LoginTest extends BaseTest {

    @Test
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test valid login redirects user to the products page")
    public void testValidLogin() {
        driver.get(BASE_URL);
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(LoginTestData.VALID_USERNAME);
        loginPage.enterPassword(LoginTestData.VALID_PASSWORD);
        loginPage.clickLogin();

        assertTrue(Objects.requireNonNull(driver.getCurrentUrl()).contains("inventory.html"));
    }

    @Test
    @Story("Invalid Login - Wrong Password")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test invalid password shows error message")
    public void testInvalidPassword() {
        driver.get(BASE_URL);
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(LoginTestData.VALID_USERNAME);
        loginPage.enterPassword(LoginTestData.INVALID_PASSWORD);
        loginPage.clickLogin();

        assertTrue(loginPage.getErrorMessage().toLowerCase().contains("epic sadface"));
    }

    @Test
    @Story("Invalid Login - Wrong Username")
    @Severity(SeverityLevel.NORMAL)
    @Description("Test invalid username shows error message")
    public void testInvalidUsername() {
        driver.get(BASE_URL);
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(LoginTestData.INVALID_USERNAME);
        loginPage.enterPassword(LoginTestData.VALID_PASSWORD);
        loginPage.clickLogin();

        assertTrue(loginPage.getErrorMessage().toLowerCase().contains("epic sadface"));
    }

    @Test
    @Story("Invalid Login - Empty Credentials")
    @Severity(SeverityLevel.MINOR)
    @Description("Test empty username and password shows error")
    public void testEmptyCredentials() {
        driver.get(BASE_URL);
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername("");
        loginPage.enterPassword("");
        loginPage.clickLogin();

        assertTrue(loginPage.getErrorMessage().toLowerCase().contains("epic sadface"));
    }

    @Test
    @Story("Invalid Login - Only Username Entered")
    @Severity(SeverityLevel.MINOR)
    @Description("Test login with only username shows error")
    public void testOnlyUsernameEntered() {
        driver.get(BASE_URL);
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername(LoginTestData.VALID_USERNAME);
        loginPage.enterPassword("");
        loginPage.clickLogin();

        assertTrue(loginPage.getErrorMessage().toLowerCase().contains("epic sadface"));
    }

    @Test
    @Story("Invalid Login - Only Password Entered")
    @Severity(SeverityLevel.MINOR)
    @Description("Test login with only password shows error")
    public void testOnlyPasswordEntered() {
        driver.get(BASE_URL);
        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername("");
        loginPage.enterPassword(LoginTestData.VALID_PASSWORD);
        loginPage.clickLogin();

        assertTrue(loginPage.getErrorMessage().toLowerCase().contains("epic sadface"));
    }
}
