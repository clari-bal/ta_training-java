# Login Test Automation - SauceDemo

## Description
Automated tests for the login form of https://www.saucedemo.com/

## Test Cases
- **UC-1:** Login with empty credentials → validates "Username is required" error
- **UC-2:** Login with username only → validates "Password is required" error  
- **UC-3:** Login with valid credentials → validates successful login to dashboard

## Technologies
- Java 17
- Selenium WebDriver
- JUnit 5
- AssertJ
- Log4j
- Maven
- WebDriverManager

## Browsers
- Chrome
- Firefox

## How to run
```bash
mvn test
```

## Author
Clarisa Baldearena