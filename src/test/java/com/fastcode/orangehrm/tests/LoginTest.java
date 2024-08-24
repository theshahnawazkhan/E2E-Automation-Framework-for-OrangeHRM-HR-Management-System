package com.fastcode.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.fastcode.orangehrm.base.BaseTest;
import com.fastcode.orangehrm.pageobjects.LoginPage;
import com.fastcode.orangehrm.utilities.ExcelUtil;
import com.fastcode.orangehrm.utilities.ExtentReportManager;

public class LoginTest extends BaseTest {

	private LoginPage loginPage;
	private static ExtentTest extentTest;
	private ExcelUtil excelUtil;
	private String username;
	private String password;
	private static final String SHEET_NAME = "OrangeHRM"; // Worksheet name

	@BeforeClass
	public void setUp() {
		super.setUp(); // Calls the setup from BaseTest
		ExtentReportManager.getInstance(); // Initialize ExtentReports instance
		extentTest = ExtentReportManager.createTest("Login Test");

		// Initialize ExcelUtil with the path to the Excel file
		excelUtil = new ExcelUtil("TestData.xlsx");

		// Retrieve data for the specific test case
		String testCaseName = "testLogin"; // Specify the test case name to retrieve data
		String[] testData = excelUtil.getDataByTestCaseName(SHEET_NAME, testCaseName);
		if (testData.length >= 3) {
			username = testData[1];
			password = testData[2];
		} else {
			throw new IllegalArgumentException("Test case data is incomplete for: " + testCaseName);
		}
	}

	@Test
	public void testLogin() throws Exception {
		try {
			loginPage = new LoginPage(driver);

			extentTest.info("Executing testLogin with username: " + username + " and password.");

			loginPage.enterUsername(username);
			loginPage.enterPassword(password);
			extentTest.info("User entered username and password");

			loginPage.clickOnLoginBtn();
			extentTest.info("User clicked on login button");

			// Validate login result
			String currentUrl = driver.getCurrentUrl();
			extentTest.info("Current URL: " + currentUrl);
			Assert.assertTrue(currentUrl.contains("dashboard"), "Login failed or redirected to unexpected URL");

			extentTest.pass("Login test passed");
		} catch (Exception e) {
			extentTest.fail("Login test failed with error: " + e.getMessage());
			throw e;
		}
	}

	@AfterClass
	public void tearDown() {
		// Quit the browser and close the WebDriver instance
		if (driver != null) {
			driver.quit();
			extentTest.info("Browser closed.");
		}
		// Close the ExcelUtil instance to release any resources
		excelUtil.close();
		ExtentReportManager.flush(); // Flush the report after the test is complete
	}
}
