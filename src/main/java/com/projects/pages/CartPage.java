package com.projects.pages;

import com.projects.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class CartPage extends BasePage {

    private final By addToCartButton = By.id("add-to-cart-sauce-labs-backpack");
    private final By removeFromCartButton = By.id("remove-sauce-labs-backpack");
    private final By cartIcon = By.className("shopping_cart_link");
    public final By cartItem = By.className("cart_item");
    private final By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void addItemToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addButton.click();
    }

    public void removeItemFromCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(removeFromCartButton)).click();
        // Wait until the remove button is gone, confirming removal
        wait.until(ExpectedConditions.invisibilityOfElementLocated(removeFromCartButton));
    }


    public void goToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement cart = wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        cart.click();
    }


    public boolean isItemInCart() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return !wait.until(driver -> driver.findElements(cartItem)).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isRemoveButtonVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(removeFromCartButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isItemAdded() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            return wait.until(driver -> {
                return !driver.findElements(removeFromCartButton).isEmpty()
                        && driver.findElement(removeFromCartButton).isDisplayed();
            });
        } catch (Exception e) {
            return false;
        }
    }


    public void clickContinueShopping() {
        driver.findElement(continueShoppingButton).click();
    }
}
