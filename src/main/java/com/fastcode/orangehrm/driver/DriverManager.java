package com.fastcode.orangehrm.driver;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.fastcode.orangehrm.config.ConfigManager;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverManager {

	private static WebDriver driver;

	public static WebDriver getDriver() {
		if (driver == null) {
			initializeDriver();
		}
		return driver;
	}

	public static void initializeDriver() {
		// Get the browser type from configuration
		String browser = ConfigManager.getInstance().getBrowser();

		if (browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else {
			throw new RuntimeException("browser new supported: " + browser);
		}
		

	    // Set the window size to a specific resolution
	    driver.manage().window().setSize(new Dimension(1920, 1080)); // Full HD resolution
	}

	public static void quitDriver() {
		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

}
