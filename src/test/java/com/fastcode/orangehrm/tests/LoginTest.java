package com.fastcode.orangehrm.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.fastcode.orangehrm.base.BaseTest;
import com.fastcode.orangehrm.pageobjects.LoginPage;
import com.fastcode.orangehrm.utilities.ExtentReportManager;

public class LoginTest extends BaseTest {

	private LoginPage loginPage;
	private static ExtentTest extentTest;

	@Override
	@BeforeClass
	public void setUp() {
		super.setUp(); // Calls the setup from BaseTest
		ExtentReportManager.getInstance();  // Initialize ExtentReports instance
		extentTest = ExtentReportManager.createTest("Login Test");
	}

	@Test
	public void testLogin() throws Exception{
		try {
			loginPage = new LoginPage(driver);
			String username = configManager.getUsername();
			String password = configManager.getPassword();

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
			extentTest.fail("Login test failed " + e.getMessage());
			throw e;
		} finally {
			ExtentReportManager.flush(); // Flush the report after the test is complete
		}
	}

}
