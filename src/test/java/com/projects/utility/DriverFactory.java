package com.projects.utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DriverFactory {
    private static WebDriver driver;
    public static final String BASE_URL = "https://www.saucedemo.com/";
    public static String chromeDataDir;

    private DriverFactory() {
        // prevent instantiation
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--disable-features=PasswordManagerEnabled,AutofillEnableAccountStorage");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-notifications");
            options.addArguments("--no-sandbox");
            options.addArguments("--remote-allow-origins=*");
            options.setExperimentalOption("prefs", prefs);
//            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
//            options.addArguments("--user-data-dir=/tmp/chrome-" + UUID.randomUUID());
//            options.addArguments("--profile-directory=Default");
            chromeDataDir = "/tmp/chrome-" + UUID.randomUUID();
            options.addArguments("--user-data-dir=" + chromeDataDir);



            driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
        return driver;
    }

    public static void openBaseUrl() {
        getDriver().get(BASE_URL);
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            try {
                Files.walk(Paths.get(chromeDataDir))
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
            driver = null; // reset for next test run
            chromeDataDir = null;
        }
    }
