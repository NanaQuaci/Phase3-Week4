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
    private final By cartItem = By.className("cart_item");
    private final By cartBadge = By.className("shopping_cart_badge");
//  private final By cartBadge = By.cssSelector("span[data-test='shopping-cart-badge']");
    private final By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void addItemToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
        getCartBadgeCount(); // ensure badge appears
    }


    public void removeItemFromCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(removeFromCartButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
    }


    public void goToCart() {
        driver.findElement(cartIcon).click();
    }

    public boolean isItemInCart() {
        return driver.findElements(cartItem).size() > 0;
    }

    public String getCartBadgeCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement badge = wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));

        // Wait until badge has non-empty text
        wait.until(driver -> !badge.getText().isEmpty());
        return badge.getText();
    }


    public void clickContinueShopping() {
        driver.findElement(continueShoppingButton).click();
    }

    public boolean isCartBadgeGone() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
        } catch (Exception e) {
            return false;
        }
    }


}
