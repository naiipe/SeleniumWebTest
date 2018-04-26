package com.gn.tests;

import static org.junit.Assert.fail;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en_old.Thurh;

public class RemoveUnavailableItems extends GNAbstractTest{
	

	@Before
	public void setUp() throws Exception {
		super.setUp();
		baseUrl = "http://localhost:7103/impuls/store/home";
		
	}

	@Test
	public void testRemoveUnavailableItems() throws Exception {
		driver.get(baseUrl + "/impuls/store/home?DPSLogout=true");
		Thread.sleep(2000); 
		driver.findElement(By.linkText("Hola Visitante")).click();
		Thread.sleep(2000); 
		driver.findElement(By.id("memberemail")).clear();
		driver.findElement(By.id("memberemail")).sendKeys("isantos@mcfadyen.com");
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys("asdfqwer");
		Thread.sleep(2000); 
		driver.findElement(By.name("/atg/userprofiling/ProfileFormHandler.login")).click();
		Thread.sleep(2000); 
		driver.findElement(By.xpath("//a/div/div[2]/span")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("button.cart-btn")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("div.radio > label")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("store-pickup-radio")).click();
		Thread.sleep(2000); 
		new Select(driver.findElement(By.id("store-to-pickup"))).selectByVisibleText("Store Two");
		Thread.sleep(2000); 
		/*driver.findElement(By.id("remove")).click();
		Thread.sleep(5000); 
		driver.findElement(By.xpath("//a/div/div[2]/span")).click();
		Thread.sleep(5000); 
		driver.findElement(By.xpath("//div[2]")).click();
		Thread.sleep(5000); 
		driver.findElement(By.cssSelector("span.loyal.loyal-default")).click();
		Thread.sleep(5000); 
		driver.findElement(By.cssSelector(".dropdown-menu li:last-child")).click();
		Thread.sleep(5000); */
	}

	@After
	public void tearDown() throws Exception {
		//driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
}
