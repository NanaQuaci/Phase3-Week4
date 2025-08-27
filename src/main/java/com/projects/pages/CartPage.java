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
//  private final By cartBadge = By.cssSelector(".shopping_cart_badge");
    private final By cartBadge = By.cssSelector("span[data-test='shopping-cart-badge']");
    private final By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void addItemToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        addButton.click();

        // Wait until the cart badge is visible and has a value
        wait.until(ExpectedConditions.textToBePresentInElementLocated(cartBadge, "1"));
    }



    public void removeItemFromCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click "Remove"
        wait.until(ExpectedConditions.elementToBeClickable(removeFromCartButton)).click();

        // Wait until "Add to cart" button comes back (product page behavior)
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
    }



    public void goToCart() {
        driver.findElement(cartIcon).click();
    }

    public boolean isItemInCart() {
        return !driver.findElements(cartItem).isEmpty();
    }


    public String getCartBadgeCount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement badge = wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge));
        return badge.getText().trim();
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
