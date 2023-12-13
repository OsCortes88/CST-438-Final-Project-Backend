package com.project.gamestore;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class SystemTestGamestore {
    // HARDCODE CHROME_DRIVER_PATH BEFORE TESTING
    public static final String CHROME_DRIVER_PATH = "";
    public static final String FRONTEND_URL = "http://localhost:3000";
    public static final String ALIAS_NAME = "test";
    // Equivalent to 1 second
    public static final int SLEEP_DURATION = 1000;
    public static final int LONGER_DURATION = 5000;

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
    public void signUpFail() throws Exception {
        // Testing with an already existing user
        String email = "test@csumb.edu";
        String firstName = "John";
        String lastName = "Doe";
        String password = "end2end";
        // Go to Sign Up page
        WebElement signUpLink = driver.findElement(By.id("signup_link"));
        signUpLink.click();
        Thread.sleep(SLEEP_DURATION);
        // Make sure we get directed to Sign Up
        assertEquals("Gamestore - Sign Up", driver.getTitle());
        // Fill out input fields
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement firstNameField = driver.findElement(By.name("firstName"));
        WebElement lastNameField = driver.findElement(By.name("lastName"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement signUpButton = driver.findElement(By.id("submit_btn"));
        emailField.sendKeys(email);
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        passwordField.sendKeys(password);
        Thread.sleep(SLEEP_DURATION);
        signUpButton.click();
        Thread.sleep(SLEEP_DURATION);
        // Make sure that the unsuccessful sign up does not redirect user to login
        // (User stays in sign up)
        assertEquals("Gamestore - Sign Up", driver.getTitle());
    }

    // Test only passes with first iteration (can not signup again after user exists)
    @Test
    public void signUp() throws Exception {
        String email = "test4@csumb.edu";
        String firstName = "John";
        String lastName = "Doe";
        String password = "end2end";
        // Go to Sign Up page
        WebElement signUpLink = driver.findElement(By.id("signup_link"));
        signUpLink.click();
        Thread.sleep(SLEEP_DURATION);
        // Make sure we get directed to Sign Up
        assertEquals("Gamestore - Sign Up", driver.getTitle());
        // Fill out input fields
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement firstNameField = driver.findElement(By.name("firstName"));
        WebElement lastNameField = driver.findElement(By.name("lastName"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement signUpButton = driver.findElement(By.id("submit_btn"));
        emailField.sendKeys(email);
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        passwordField.sendKeys(password);
        Thread.sleep(SLEEP_DURATION);
        signUpButton.click();
        Thread.sleep(SLEEP_DURATION);
        // Make sure that the successful sign up redirects user to login
        assertEquals("Gamestore - Login", driver.getTitle());
        // Check if new user can log in.
        WebElement loginEmailField = driver.findElement(By.name("username"));
        WebElement loginPasswordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.id("submit_btn"));
        loginEmailField.sendKeys(email);
        loginPasswordField.sendKeys(password);
        Thread.sleep(SLEEP_DURATION);
        loginButton.click();
        Thread.sleep(SLEEP_DURATION);
        assertEquals("Gamestore - Main Page", driver.getTitle());
    }

    @Test
    public void badLogin() throws Exception {
        // Test with non-registered user
        String email = "dne@csumb.edu";
        String password = "secret";
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
        // User stays in Login page if login is unsuccessful
        assertEquals("Gamestore - Login", driver.getTitle());
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
        assertEquals("Gamestore - Main Page", driver.getTitle());
    }

    @Test
    public void logout() throws Exception {
        // First login to main page with existing user
        String email = "test@csumb.edu";
        String password = "user";
        WebElement emailField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.id("submit_btn"));
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        Thread.sleep(SLEEP_DURATION);
        loginButton.click();
        Thread.sleep(SLEEP_DURATION);
        // A successful login will redirect user to main page
        assertEquals("Gamestore - Main Page", driver.getTitle());
        // Click on logout link
        WebElement logoutLink = driver.findElement(By.id("logoutLink"));
        logoutLink.click();
        Thread.sleep(SLEEP_DURATION);
        // Check that user gets sent back to login
        assertEquals("Gamestore - Login", driver.getTitle());
    }

    // Testing with first game in Featured Games (game 3498)
    @Test
    public void addGameToWishlist() throws Exception {
        // First login with existing user
        String email = "test@csumb.edu";
        String password = "user";
        WebElement emailField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.id("submit_btn"));
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        Thread.sleep(SLEEP_DURATION);
        loginButton.click();
        Thread.sleep(SLEEP_DURATION);
        // Now that we are on MainPage, begin adding game to wishlist
        assertEquals("Gamestore - Main Page", driver.getTitle());
        // Allow for more time for unique key to be generated
        Thread.sleep(LONGER_DURATION);
        // Grabbing img and addButton from first featured game (w/ id 3498)
        WebElement clickOnGame = driver.findElement(By.id("img3498"));
        clickOnGame.click();
        // Allow for unique button id to be generated.
        Thread.sleep(LONGER_DURATION);
        WebElement addButton = driver.findElement(By.id("3498"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addButton);
        // Allow for driver to scroll down
        Thread.sleep(LONGER_DURATION);
        // Game is added
        addButton.click();
        // Check Wishlist
        WebElement closeModal = driver.findElement(By.className("close"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", closeModal);
        // Allow for driver to scroll up
        Thread.sleep(SLEEP_DURATION);
        closeModal.click();
        WebElement wishlsitLink = driver.findElement(By.id("wishlistLink"));
        wishlsitLink.click();
        Thread.sleep(SLEEP_DURATION);
        // In wishlist there should be game 3498 (Grand Theft Auto V).
        WebElement gameTitle = driver.findElement(By.id("Grand Theft Auto V"));
        // gameTitle should not be null.
        assertNotNull(gameTitle);
    }

    @Test
    public void deleteGameFromWishlist() throws Exception {
        // First login with existing user
        String email = "test@csumb.edu";
        String password = "user";
        WebElement emailField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.id("submit_btn"));
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        Thread.sleep(SLEEP_DURATION);
        loginButton.click();
        Thread.sleep(SLEEP_DURATION);
        // Add game with id 3498
        assertEquals("Gamestore - Main Page", driver.getTitle());
        Thread.sleep(LONGER_DURATION);
        WebElement clickOnGame = driver.findElement(By.id("img3498"));
        clickOnGame.click();
        Thread.sleep(LONGER_DURATION);
        WebElement addButton = driver.findElement(By.id("3498"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addButton);
        Thread.sleep(LONGER_DURATION);
        addButton.click();
        // In wishlist, find game 3498 (Grand Theft Auto V) and delete it
        WebElement closeModal = driver.findElement(By.className("close"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", closeModal);
        Thread.sleep(SLEEP_DURATION);
        closeModal.click();
        WebElement wishlsitLink = driver.findElement(By.id("wishlistLink"));
        wishlsitLink.click();
        Thread.sleep(SLEEP_DURATION);
        WebElement wishlistGame = driver.findElement(By.id("Grand Theft Auto V"));
        wishlistGame.click();
        Thread.sleep(LONGER_DURATION);
        WebElement deleteButton = driver.findElement(By.name("submit"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteButton);
        Thread.sleep(LONGER_DURATION);
        deleteButton.click();
        Thread.sleep(SLEEP_DURATION);
        // Assert that no element of game 3498 is found after deletion.
        assertThrows(NoSuchElementException.class, () -> {driver.findElement(By.id("Grand Theft Auto V"));});
    }

    @AfterEach
    public void cleanup() {
        if(driver != null) {
            driver.close();
            driver.quit();
            driver = null;
        }
    }
}
