package com.projects.utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;
import java.util.*;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static final String BASE_URL = "https://www.saucedemo.com/";

    private DriverFactory() {
        // prevent instantiation
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = System.getenv("BROWSER");
            if (browser == null) browser = "chrome";

            switch (browser.toLowerCase()) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();

                    Map<String, Object> chromePrefs = new HashMap<>();
                    chromePrefs.put("credentials_enable_service", false);
                    chromePrefs.put("profile.password_manager_enabled", false);

                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--disable-save-password-bubble");
                    chromeOptions.addArguments("--disable-features=PasswordManagerEnabled,AutofillEnableAccountStorage");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--disable-popup-blocking");
                    chromeOptions.addArguments("--disable-extensions");
                    chromeOptions.addArguments("--disable-notifications");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--headless=new");

                    driver.set(new org.openqa.selenium.chrome.ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();

                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("--width=1920");
                    firefoxOptions.addArguments("--height=1080");
                    firefoxOptions.addArguments("--headless");

                    driver.set(new org.openqa.selenium.firefox.FirefoxDriver(firefoxOptions));
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver.get();
    }

    public static void openBaseUrl() {
        getDriver().get(BASE_URL);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
        }
        driver.remove();
    }
}


// mvn clean test -Denv=local -Dbrowser=chrome