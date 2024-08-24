# E2E Automation Framework for OrangeHRM HR Management System

## Project Overview

This project is an End-to-End (E2E) Test Automation Framework designed for testing the OrangeHRM HR Management System. The framework uses Selenium WebDriver for web automation, TestNG for test management, and ExtentReports for reporting. It includes functionalities for login, data-driven testing using Excel, and comprehensive test reporting.

## Project Structure

- `src/main/java`: Contains the main source code for the framework, including page objects, utility classes, and configuration management.
- `src/test/java`: Contains test classes and data-driven tests.
- `test-data/`: Contains Excel files for data-driven testing.
- `pom.xml`: Maven configuration file for managing dependencies.
- `testng.xml`: TestNG configuration file for managing test suites and execution.

## Prerequisites

- Java Development Kit (JDK) 1.7 or higher
- Apache Maven
- Selenium WebDriver
- TestNG
- Apache POI (for handling Excel files)
- ExtentReports (for test reporting)

## Setup Instructions

1. **Clone the Repository**

    ```bash
    git clone <repository-url>
    cd E2E-Automation-Framework-for-OrangeHRM-HR-Management-System
    ```

2. **Install Dependencies**

    Ensure you have Maven installed. Run the following command to install the required dependencies:

    ```bash
    mvn clean install
    ```

3. **Setup Test Data**

    Place your `TestData.xlsx` file in the `test-data/` directory. Ensure it contains the required test data in the specified format:

    ```plaintext
    TestCaseName    Username    Password
    testLogin       admin       admin123
    ```

## Usage

1. **Running Tests**

    You can run the tests using Maven:

    ```bash
    mvn test
    ```

    This command will execute the tests as defined in the `testng.xml` configuration file.

2. **Generating Reports**

    After test execution, ExtentReports will generate a detailed HTML report. You can find the report in the `target` directory, typically under `target/extent-reports/`.

## Test Configuration

- **Base URL**: The base URL for the OrangeHRM application is configured in the `ConfigManager` class.
- **Excel Data Source**: The `ExcelUtil` class is used to read test data from Excel files. Test data is specified in the `test-data/TestData.xlsx` file.

## Example Test Case

Here's a simple example of a test case using the `LoginTest` class:

```java
@Test
public void testLogin() throws Exception {
    loginPage = new LoginPage(driver);
    String username = configManager.getUsername();
    String password = configManager.getPassword();

    loginPage.enterUsername(username);
    loginPage.enterPassword(password);
    loginPage.clickOnLoginBtn();

    String currentUrl = driver.getCurrentUrl();
    Assert.assertTrue(currentUrl.contains("dashboard"), "Login failed or redirected to unexpected URL");
}

