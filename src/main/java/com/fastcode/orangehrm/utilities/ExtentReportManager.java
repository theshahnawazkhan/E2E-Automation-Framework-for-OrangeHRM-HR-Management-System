package com.fastcode.orangehrm.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

	private static ExtentReports extent;
	private static ExtentTest test;
	private static WebDriver driver;

	public static ExtentReports getInstance() {
		if (extent == null) {
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
			String reportPath = System.getProperty("user.dir") + "/test-output/Extent-Report/ExtentReport_" + timestamp
					+ ".html";
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

			sparkReporter.config().setTheme(Theme.STANDARD);
			sparkReporter.config().setDocumentTitle("Automation Test Report");
			sparkReporter.config().setReportName("OrangeHRM Test Report");

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Host Name", "Localhost");
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("User Name", "Shahnawaz Khan");
		}
		return extent;
	}

	public static ExtentTest createTest(String testName, WebDriver driverInstance) {
		test = extent.createTest(testName);
		driver = driverInstance; // Set the WebDriver instance here
		return test;
	}

	public static void flush() {
		if (extent != null) {
			extent.flush();
		}

	}

	public static void addScreenshot(String stepDescription) {
		if (driver != null) { // Ensures the driver is not null
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String screenshotPath = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis()
					+ ".png";

			try {
				FileHandler.copy(screenshotFile, new File(screenshotPath));
				test.info(stepDescription, MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			test.warning("WebDriver instance is null, cannot capture screenshot.");
		}

	}

}
