package com.gn.tests;

import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GetUserTokenTest extends GNAbstractTest{
	

	@Before
	public void setUp() throws Exception {
		super.setUp();
		baseUrl = "http://localhost:7103";
		
	}

	@Test
	public void testRegister() throws Exception {
		driver.manage().window().maximize();
		
		driver.get(baseUrl + "/dyn/admin/");
		Thread.sleep(5000);
		
		
		
		/*Alert alert=driver.switchTo().alert();
		driver.switchTo().alert().sendKeys("Helllo");
		alert.accept();
		System.out.println(alert.getText());*/
		
		/*WebDriverWait wait = new WebDriverWait(driver, 10);      
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());  
		Thread.sleep(3000);
		alert.authenticateUsing(new UserAndPassword("admin","Admin123"));*/
		
		
	    driver.findElement(By.linkText("Component Browser")).click();
	    driver.findElement(By.linkText("Search")).click();
	    driver.findElement(By.id("searchField")).clear();
	    driver.findElement(By.id("searchField")).sendKeys("UserProfile");
	    driver.findElement(By.id("searchField")).sendKeys(Keys.RETURN);
	    Thread.sleep(3000);
	    driver.findElement(By.linkText("/atg/registry/ContentRepositories/UserProfiles")).click();
	    Thread.sleep(3000);
	    driver.findElement(By.name("xmltext")).clear();
	    
	    driver.findElement(By.name("xmltext")).sendKeys("<query-items item-descriptor=\"user\">email = \""+"isantos@mcfadyen.com"+"\"</query-items>");
	   
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		    
		    
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
