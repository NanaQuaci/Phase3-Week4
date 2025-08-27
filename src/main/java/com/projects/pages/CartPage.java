package com.projects.pages;

import com.projects.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {

    private final By addToCartButton = By.id("add-to-cart-sauce-labs-backpack");
    private final By removeFromCartButton = By.id("remove-sauce-labs-backpack");
    private final By cartIcon = By.className("shopping_cart_link");
    private final By cartItem = By.className("cart_item");
//  private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartBadge = By.cssSelector("span[data-test='shopping-cart-badge']");
    private final By continueShoppingButton = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void addItemToCart() {
        driver.findElement(addToCartButton).click();
    }

    public void removeItemFromCart() {
        driver.findElement(removeFromCartButton).click();
    }

    public void goToCart() {
        driver.findElement(cartIcon).click();
    }

    public boolean isItemInCart() {
        return driver.findElements(cartItem).size() > 0;
    }

    public String getCartBadgeCount() {
        return driver.findElement(cartBadge).getText();
    }

    public void clickContinueShopping() {
        driver.findElement(continueShoppingButton).click();
    }

    public boolean isCartBadgeGone() {
        return driver.findElements(cartBadge).isEmpty();
    }

}
