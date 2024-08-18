package com.fastcode.orangehrm.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager {

	private static ExtentReports extent;
	private static ExtentTest test;

	public static ExtentReports getInstance() {
		if (extent == null) {
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = System.getProperty("user.dir") + "/test-output/Extent-Report/ExtentReport_" + timestamp + ".html";
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

	public static ExtentTest createTest(String testName) {
		test = extent.createTest(testName);
		return test;
	}

	public static void flush() {
		if (extent != null) {
			extent.flush();
		}

	}

}
