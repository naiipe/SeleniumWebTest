package com.gn.tests;

import static org.junit.Assert.fail;

import java.time.LocalDateTime;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;

public class OutOfStockItem extends GNAbstractTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		baseUrl = "http://localhost:7103";
		
	}

	@Test
	public void testOutOfStockItem() throws Exception {
		String sku = productSKUId();
		System.out.println(sku);
		changeStock(sku, false);
		Thread.sleep(2000);
		selectCheckbox();
		changeStock(sku, true);
		runScheduler();
	}
	public void runScheduler() throws Exception{
		driver.get(baseUrl + "/dyn/admin/atg/dynamo/admin/en/?_requestid=212");
	    driver.findElement(By.linkText("Component Browser")).click();
	    driver.findElement(By.linkText("Search")).click();
	    driver.findElement(By.id("searchField")).clear();
	    driver.findElement(By.id("searchField")).sendKeys("InStockEmailSenderScheduler");
	    driver.findElement(By.id("searchField")).sendKeys(Keys.RETURN);
	    driver.findElement(By.xpath("(//input[@name='showAll'])[2]")).click();
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    Thread.sleep(10000);
	    driver.findElement(By.linkText("/gn/email/InStockEmailSenderScheduler")).click();
	    driver.findElement(By.linkText("processInStockProducts")).click();
	    driver.findElement(By.name("submit")).click();
	    Thread.sleep(2000);
	    System.out.println("Check your email");
	}
	public void selectCheckbox() throws Exception{
		driver.get(baseUrl + "/impuls/store/search");
		Thread.sleep(2000);
	    driver.findElement(By.xpath("(//button[@type='button'])[7]")).click();
    	driver.findElement(By.linkText("Precio H a L")).click();
	    driver.findElement(By.cssSelector("img.img-responsive.lazy")).click();
		Thread.sleep(2000);
		driver.findElement(By.cssSelector("input.triggerlogin")).click();
		driver.findElement(By.id("memberemail")).sendKeys("mlaureano@mcfadyen.com");
		driver.findElement(By.id("password")).sendKeys("Admin123");
		driver.findElement(By.name("/atg/userprofiling/ProfileFormHandler.login")).click();
		String currentUrl = driver.getCurrentUrl();
		driver.get(currentUrl);
		Thread.sleep(2000);
	}
	
	public void login() throws Exception{
		driver.get(baseUrl + "/impuls/store/home?DPSLogout=true");
		Thread.sleep(3000); 
		driver.findElement(By.linkText("Hola Visitante")).click();
		Thread.sleep(3000); 
		
		driver.findElement(By.id("memberemail")).sendKeys("mlaureano@mcfadyen.com");
		
		driver.findElement(By.id("password")).sendKeys("Admin123");
		driver.findElement(By.name("/atg/userprofiling/ProfileFormHandler.login")).click();
	}
	
	public String productSKUId() throws Exception{
		driver.get(baseUrl + "/impuls/store/search");
		Thread.sleep(2000);
	    driver.findElement(By.xpath("(//button[@type='button'])[7]")).click();
    	driver.findElement(By.linkText("Precio H a L")).click();
	    driver.findElement(By.cssSelector("img.img-responsive.lazy")).click();
		Thread.sleep(2000);
		String sku = driver.findElement(By.cssSelector("div.product-code.bold > span")).getText();
	
		return sku;
	}
	
	public void changeStock(String sku, boolean stock) throws Exception{
		driver.get(baseUrl + "/dyn/admin/");
		Thread.sleep(5000);
		driver.findElement(By.linkText("Component Browser")).click();
		driver.findElement(By.linkText("Search")).click();
		driver.findElement(By.id("searchField")).clear();
		driver.findElement(By.id("searchField")).sendKeys("InventoryRepository");
		driver.findElement(By.id("searchField")).sendKeys(Keys.RETURN);
		Thread.sleep(3000);
		driver.findElement(By.linkText("/atg/commerce/inventory/InventoryRepository")).click();
		Thread.sleep(3000);
		driver.findElement(By.name("xmltext")).clear();
		driver.findElement(By.name("xmltext")).sendKeys(
				"<query-items item-descriptor=\"inventory\" id-only=\"false\">catalogRefId = \"" + sku + "\"</query-items>");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		Thread.sleep(2000);
		String id = ((driver.findElement(By.cssSelector("code")).getText()));
		int inicio = id.indexOf("id=\"");
		id = id.substring(inicio+4,inicio+11+4);
		System.out.println(id);
		if(!stock){ 		//change to OUTOFSTOCK
			outOfStock(id);
		}else{				//change to INSTOCK
			inStock(id);
		}
	}
	
	public void inStock(String id) throws Exception{
		driver.findElement(By.name("xmltext")).clear();
		driver.findElement(By.name("xmltext")).sendKeys(
				"<update-item id=\""+id+"\" item-descriptor=\"inventory\">"
						+ "<set-property name=\"availabilityStatus\" ><![CDATA[INSTOCK]]></set-property>"
						+ "<set-property name=\"stockLevel\" ><![CDATA[20]]></set-property>"
						+ "</update-item>");
		
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		Thread.sleep(2000);
	}
	
	public void outOfStock(String id) throws Exception{
		driver.findElement(By.name("xmltext")).clear();
		driver.findElement(By.name("xmltext")).sendKeys(
				"<update-item id=\""+id+"\" item-descriptor=\"inventory\">"
						+ "<set-property name=\"availabilityStatus\" ><![CDATA[OUTOFSTOCK]]></set-property>"
						+ "<set-property name=\"stockLevel\" ><![CDATA[0]]></set-property>"
						+ "</update-item>");
		
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		Thread.sleep(2000);
		driver.quit();
	}
	
	
	
	
	@After
	public void tearDown() throws Exception {
		// driver.quit();
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
