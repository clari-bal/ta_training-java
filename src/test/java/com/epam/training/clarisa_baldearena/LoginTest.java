package com.epam.training.clarisa_baldearena;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.time.Duration;
import java.util.stream.Stream;

public class LoginTest {

    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    private WebDriver driver;

    static Stream<String> browserProvider() {
        return Stream.of("chrome", "firefox");
    }

    void initDriver(String browser) {
        logger.info("Launching browser: " + browser);
        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }
        driver.get("https://www.saucedemo.com/");
        logger.info("Browser launched, login page");
    }

    @AfterEach
    void tearDown() {
        logger.info("Closing browser...");
        if (driver != null) {
            driver.quit();
        }
        logger.info("Closed browser");
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    void givenEmptyCredentials_whenClickLogin_thenUsernameErrorIsShown(String browser) {
        initDriver(browser);
        logger.info("UC-1: Test Login form with empty credentials");
        
        // GIVEN - user is on the login page with some credentials typed
        driver.findElement(By.xpath("//*[@id='user-name']")).sendKeys("someuser");
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys("somepassword");
        
        // WHEN - user clears the inputs and clicks Login
        driver.findElement(By.xpath("//*[@id='user-name']")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.xpath("//*[@id='user-name']")).sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//*[@id='login-button']")).click();
        
        // THEN - error message "Username is required" is shown
        String errorMessage = driver.findElement(By.xpath("//*[@data-test='error']")).getText();
        logger.info("Error message displayed: " + errorMessage);
        assertThat(errorMessage).contains("Username is required");
    }
 
    @ParameterizedTest
    @MethodSource("browserProvider")    
    void givenNoPassword_whenClickLogin_thenPasswordErrorIsShown(String browser) {
        initDriver(browser);
        logger.info("UC-2: Test Login form with credentials by passing Username");
        
        // GIVEN - user is on the login page with some credentials typed
        driver.findElement(By.xpath("//*[@id='user-name']")).sendKeys("someuser");
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys("somepassword");
        
        // WHEN - user clears the password input and clicks Login
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys(Keys.CONTROL + "a");
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys(Keys.DELETE);
        driver.findElement(By.xpath("//*[@id='login-button']")).click();
        
        // THEN - error message "Password is required" is shown
        String errorMessage = driver.findElement(By.xpath("//*[@data-test='error']")).getText(); 
        logger.info("Error message displayed: " + errorMessage);
        assertThat(errorMessage).contains("Password is required");
    }

    @ParameterizedTest
    @MethodSource("browserProvider")
    void givenValidCredentials_whenClickLogin_thenDashboardIsShown(String browser) {
        initDriver(browser);
        logger.info("UC-3: Test Login form with credentials by passing Username and Password");
        
        // GIVEN - user is on the login page with valid credentials
        driver.findElement(By.xpath("//*[@id='user-name']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//*[@id='password']")).sendKeys("secret_sauce");
        
        // WHEN - user clicks Login button
        driver.findElement(By.xpath("//*[@id='login-button']")).click();
        
        // THEN - dashboard is shown with title "Swag Labs"
        String pageTitle = driver.getTitle();
        logger.info("Page title: " + pageTitle);
        assertThat(pageTitle).isEqualTo("Swag Labs");
        assertThat(driver.getCurrentUrl()).contains("inventory");
    } 
}