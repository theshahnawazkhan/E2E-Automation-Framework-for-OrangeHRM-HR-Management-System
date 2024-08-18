package com.fastcode.orangehrm.pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	private WebDriver driver;
	private WebDriverWait wait;

	private By usernameXPath = By.xpath("//input[@name='username']");
	private By passwordXPath = By.xpath("//input[@name='password']");
	private By loginBtnXPath = By.xpath("//button[@type='submit']");

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}

	public void enterUsername(String username) {
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.visibilityOfElementLocated(usernameXPath));
		driver.findElement(usernameXPath).sendKeys(username);
		;
	}

	public void enterPassword(String password) {
		driver.findElement(passwordXPath).sendKeys(password);
	}

	public void clickOnLoginBtn() throws Exception{
		driver.findElement(loginBtnXPath).click();
	}

}
