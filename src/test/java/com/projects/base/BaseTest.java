package com.projects.base;

import com.projects.utility.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        driver = DriverFactory.getDriver();
        DriverFactory.openBaseUrl();
    }

    @AfterEach
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
