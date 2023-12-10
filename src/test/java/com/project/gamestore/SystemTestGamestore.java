package com.project.gamestore;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SystemTestGamestore {
    // HARDCODE CHROME_DRIVER_PATH
    public static final String CHROME_DRIVER_PATH = "";
    public static final String FRONTEND_URL = "http://localhost:3000";
    public static final String ALIAS_NAME = "test";
    // Equivalent to 1 second
    public static final int SLEEP_DURATION = 1000;

    WebDriver driver;

    @BeforeEach
    public void setup() throws Exception {
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get(FRONTEND_URL);
        // Wait a while for frontend to load
        Thread.sleep(SLEEP_DURATION);
    }

    @Test
    public void login() throws Exception {
        // Test hardcoded user
        String email = "test@csumb.edu";
        String password = "user";
        WebElement emailField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.id("submit_btn"));
        // Send input
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        Thread.sleep(SLEEP_DURATION);
        // Click on login button
        loginButton.click();
        Thread.sleep(SLEEP_DURATION);
        // A successful login will redirect user to main page
        assertEquals(driver.getTitle(), "Gamestore - Main Page");
    }

    // TODO: test sign-up user interface

    // TODO: test add-to-cart buttons

    // TODO (once genre branch is merged): test if genre filters are applied to MainPage

    @AfterEach
    public void cleanup() {
        if(driver != null) {
            driver.close();
            driver.quit();
            driver = null;
        }
    }
}
