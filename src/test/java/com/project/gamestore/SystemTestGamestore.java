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
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    // TODO: test sign-up user interface
    @Test
    public void signUpFail() throws Exception {
        String email = "test@csumb.edu";
        String firstName = "John";
        String lastName = "Doe";
        String password = "end2end";

        WebElement emailField = driver.findElement(By.name("username"));
        WebElement firstNameField = driver.findElement(By.name("firstName"));
        WebElement lastNameField = driver.findElement(By.name("lastName"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement signUpButton = driver.findElement(By.id("submit_btn"));

        emailField.sendKeys(email);
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        passwordField.sendKeys(password);

        signUpButton.click();
        Thread.sleep(SLEEP_DURATION);

        WebElement userExistsMessage = driver.findElement(By.className("user-exists-message"));
        assertTrue(userExistsMessage.isDisplayed());

    }
    @Test
    public void signUp() throws Exception {
        String email = "test1@csumb.edu";
        String firstName = "John";
        String lastName = "Doe";
        String password = "end2end";

        WebElement emailField = driver.findElement(By.name("username"));
        WebElement firstNameField = driver.findElement(By.name("firstName"));
        WebElement lastNameField = driver.findElement(By.name("lastName"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement signUpButton = driver.findElement(By.id("submit_btn"));

        emailField.sendKeys(email);
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        passwordField.sendKeys(password);

        signUpButton.click();
        Thread.sleep(SLEEP_DURATION);

        assertEquals(driver.getTitle(), "Gamestore - Login");
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

    // TODO: test add-to-cart buttons
    @Test
    public void addGameToWishlist() throws Exception {
        driver.get(FRONTEND_URL + "/mainpage");
        Thread.sleep(SLEEP_DURATION);

        WebElement addBtn = driver.findElement(By.className("add_btn"));
        addBtn.click();
        Thread.sleep(SLEEP_DURATION);

        WebElement successMessage = driver.findElement(By.className("success-message"));
        assertTrue(successMessage.isDisplayed());
    }

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
