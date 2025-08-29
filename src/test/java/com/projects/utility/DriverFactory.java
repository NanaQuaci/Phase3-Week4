package com.projects.utility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static final String BASE_URL = "https://www.saucedemo.com/";
    private static ThreadLocal<String> dataDir = new ThreadLocal<>();

    private DriverFactory() {
        // prevent instantiation
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = System.getenv("BROWSER");
            if (browser == null) browser = "chrome";

//            String uniqueDir = "/tmp/" + browser + "-" + UUID.randomUUID();
//            dataDir.set(uniqueDir);

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
//                    chromeOptions.addArguments("--user-data-dir=" + uniqueDir);
//                    chromeOptions.setExperimentalOption("prefs", chromePrefs);

                    driver.set(new org.openqa.selenium.chrome.ChromeDriver(chromeOptions));
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();

                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("--disable-notifications");
                    edgeOptions.addArguments("--disable-extensions");
                    edgeOptions.addArguments("--no-sandbox");
                    edgeOptions.addArguments("--window-size=1920,1080");
                    edgeOptions.addArguments("--headless=new");
//                    edgeOptions.addArguments("--user-data-dir=" + uniqueDir);

                    driver.set(new org.openqa.selenium.edge.EdgeDriver(edgeOptions));
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser);
            }

            driver.get().manage().window().maximize();
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
            try {
                Files.walk(Paths.get(dataDir.get()))
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(File::delete);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        driver.remove();
        dataDir.remove();
    }
}


// mvn clean test -Denv=local -Dbrowser=chrome