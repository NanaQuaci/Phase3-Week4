package com.projects.base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }



    public void handleChromePasswordModal() {
        try {
            driver.switchTo().frame("credential_picker_iframe");
            WebElement okButton = driver.findElement(By.id("submit"));
            okButton.click();
            System.out.println("Password modal dismissed successfully.");
        } catch (Exception e) {
            System.out.println("No password modal detected.");
        } finally {
            driver.switchTo().defaultContent();
        }
    }


}
