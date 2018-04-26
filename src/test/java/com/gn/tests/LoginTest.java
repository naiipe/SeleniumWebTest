package com.gn.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class LoginTest extends GNAbstractTest{
	

	@Before
	public void setUp() throws Exception {
		super.setUp();
		baseUrl = "https://www.americanas.com.br";
		
	}

	@Test
	public void testLookForNotebook() throws Exception {
		
		System.out.println("Looking for Notebook...");
		driver.get(baseUrl);
		Thread.sleep(4000);
		driver.findElement(By.id("h_search-input")).clear();
		driver.findElement(By.id("h_search-input")).sendKeys("notebook");
		Thread.sleep(1000);
		driver.findElement(By.id("h_search-btn")).click();
		Thread.sleep(2000);
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,-250)", "");
		Thread.sleep(2000);
		driver.findElement(By.id("icon-check")).click();
		Thread.sleep(4000);
		
		/*
		driver.get(baseUrl + "/impuls/store/home?DPSLogout=true");
		driver.findElement(By.linkText("Hola Visitante")).click();
		Thread.sleep(3000); 
		
		driver.findElement(By.id("memberemail")).sendKeys("mlaureano@mcfadyen.com");
		
		driver.findElement(By.id("password")).sendKeys("Admin123");
		driver.findElement(By.name("/atg/userprofiling/ProfileFormHandler.login")).click();
		*/
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		/*String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}*/
	}

	/*private boolean isElementPresent(By by) {
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
	}*/
}
