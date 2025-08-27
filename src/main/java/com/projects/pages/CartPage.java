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
        driver.findElement(addToCartButton).click();
        getCartBadgeCount();
    }

    public void removeItemFromCart() {
        driver.findElement(removeFromCartButton).click();
        isCartBadgeGone();
    }

    public void goToCart() {
        driver.findElement(cartIcon).click();
    }

    public boolean isItemInCart() {
        return driver.findElements(cartItem).size() > 0;
    }

    public String getCartBadgeCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Wait for the badge to be present and have non-empty text
        WebElement badge = wait.until(driver -> {
            WebElement e = driver.findElement(cartBadge);
            return (!e.getText().isEmpty()) ? e : null;
        });
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
