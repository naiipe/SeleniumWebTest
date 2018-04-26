package com.gn.tests;

import static org.junit.Assert.fail;

import java.util.Date;
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

public class RegisterTest extends GNAbstractTest{
	

	@Before
	public void setUp() throws Exception {
		super.setUp();
		baseUrl = "http://http://115.110.176.68:7103/impuls/store/home";
		
	}

	@Test
	public void testRegister() throws Exception {
		Date now = new Date();
		Thread.sleep(3000); 
		
		 driver.get(baseUrl + "/impuls/store/home");
		 Thread.sleep(3000);   
		 driver.findElement(By.cssSelector("span.caret")).click();
		 
		 //driver.findElement(By.linkText("Registro de nuevos miembros")).click();
		 
		 driver.findElement(By.cssSelector(".dropdown-menu li:nth-child(2)")).click();
		 Thread.sleep(3000);   
		 
		 driver.findElement(By.name("/atg/userprofiling/ProfileFormHandler.value.firstName")).sendKeys("Ivan");
		 
		  driver.findElement(By.id("form-paternal")).sendKeys("Filipe");
		  
		    driver.findElement(By.id("form-maternal")).sendKeys("Salamanca");
		    String email = "isantos"+now.getYear()+now.getMonth()+now.getDay()+now.getHours()+now.getMinutes()+"@mcfadyen.com";
		    
		    driver.findElement(By.id("form-email")).sendKeys(email);
			System.out.println(email);
		    
		    driver.findElement(By.id("register_password")).sendKeys("asdfqwer");
		    
		    driver.findElement(By.cssSelector("#frmregister > div.form-content > div.form-group.col-md-12 > #confirmpassword")).sendKeys("asdfqwer");
		   
		    driver.findElement(By.id("inlineRadio1")).click();
		  
		    driver.findElement(By.id("SendRegisterButton")).click();
		    Thread.sleep(3000);
		    
		    
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
