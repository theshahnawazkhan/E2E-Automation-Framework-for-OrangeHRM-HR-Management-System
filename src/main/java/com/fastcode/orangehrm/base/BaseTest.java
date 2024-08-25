package com.fastcode.orangehrm.base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.fastcode.orangehrm.config.ConfigManager;
import com.fastcode.orangehrm.driver.DriverManager;

public class BaseTest {

	protected WebDriver driver;
	protected ConfigManager configManager;

	@BeforeClass
	public void setUp() {
		configManager = ConfigManager.getInstance();
		driver = DriverManager.getDriver();
		driver.get(configManager.getLoginUrl());

	}

	@AfterClass
	public void tearDown() {
		DriverManager.quitDriver();
	}

}
