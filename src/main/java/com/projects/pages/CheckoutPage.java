package com.projects.pages;

import com.projects.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class CheckoutPage extends BasePage {
    // Locators
    private By checkoutButton = By.cssSelector("button[data-test='checkout']");
    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By postalCodeField = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By finishButton = By.id("finish");
    private By errorMessage = By.cssSelector("h3[data-test='error']");
    private By overviewTitle = By.cssSelector(".title"); // "Checkout: Overview"
    private By completeHeader = By.cssSelector(".complete-header"); // "Thank you for your order!"
    private By cartIcon = By.cssSelector("a[data-test='shopping-cart-link']");
    private By cartPageContainer = By.id("cart_contents_container");
    public final String CHECKOUT_URL = "https://www.saucedemo.com/checkout-step-one.html";

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }


    // Actions
    public void goToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartPageContainer));
    }

    public void clickCheckout() {
        WebElement checkout = wait.until(ExpectedConditions.presenceOfElementLocated(checkoutButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkout);
        wait.until(ExpectedConditions.visibilityOf(checkout));
        wait.until(ExpectedConditions.elementToBeClickable(checkout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartPageContainer));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".title"), "Your Cart"));
        new Actions(driver).moveToElement(checkout).click().perform();
    }



    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).clear();
        driver.findElement(firstNameField).sendKeys(firstName);
    }


    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameField)).clear();
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    public void enterPostalCode(String postalCode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(postalCodeField)).clear();
        driver.findElement(postalCodeField).sendKeys(postalCode);
    }

    public void clickContinue() {
        WebElement continueBtn = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        try {
            continueBtn.click();
        } catch (Exception e) {
            handleChromePasswordModal(); // dismiss the Chrome save-password popup
            continueBtn.click();
        }
    }


    public void clickFinish() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(overviewTitle));

        WebElement finish = wait.until(ExpectedConditions.presenceOfElementLocated(finishButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", finish);

        wait.until(ExpectedConditions.visibilityOf(finish));
        wait.until(ExpectedConditions.elementToBeClickable(finish));

        try {
            finish.click();
        } catch (Exception e) {
            handleChromePasswordModal(); // Call BasePage method
            finish.click();
        }
    }





    // Validations
    public String getErrorMessage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage))
                .getText()
                .trim();
    }

    public boolean isOnOverviewPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(overviewTitle))
                .getText().equalsIgnoreCase("Checkout: Overview");
    }

    public void navigateToCheckoutPage() {
        driver.get(CHECKOUT_URL);
    }

    public boolean isOrderComplete() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader))
                .getText().equalsIgnoreCase("Thank you for your order!");
    }

    public void waitForCheckoutPageToLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField));
    }

}
