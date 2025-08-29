package com.projects.hooks;

import com.projects.utility.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hooks {
    private WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverFactory.getDriver();
        DriverFactory.openBaseUrl();
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}

