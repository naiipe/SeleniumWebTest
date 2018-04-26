package com.gn.tests;

import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class GN1423 extends GNAbstractTest {

	private String baseUrl2;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		//baseUrl = "http://115.110.176.68:7103";
		//baseUrl ="http://129.144.156.80:7103";
		//baseUrl = "http://192.168.0.24:7103";
		
		/*local/baseUrl = "http://localhost:7103";
		baseUrl2 = "http://localhost:7203";/**/
		
		/*UAT*baseUrl = "http://129.144.156.80:7103";
		baseUrl2 = "http://129.144.156.80:7103";/**/
		
		
		/*QA*/ baseUrl = "http://172.21.10.15:7103";
		baseUrl2 = "http://172.21.10.15:7103";/**/	
	}

	@Test
	public void testGN1423() throws Exception {

		logger.info("url : " + baseUrl);

		//logs an exception thrown from somewhere
		//logger.error("This is error", baseUrl2);

		
		LocalDateTime today = LocalDateTime.now();
		
		Thread.sleep(3000);
		driver.manage().window().maximize();
		driver.get(baseUrl + "/impuls/store/home");
		Thread.sleep(3000);
		driver.findElement(By.cssSelector("span.caret")).click();

		// driver.findElement(By.linkText("Registro de nuevos
		// miembros")).click();

		driver.findElement(By.cssSelector(".dropdown-menu li:nth-child(2)")).click();
		Thread.sleep(3000);

		driver.findElement(By.name("/atg/userprofiling/ProfileFormHandler.value.firstName")).sendKeys("Ivan");

		driver.findElement(By.id("form-paternal")).sendKeys("Filipe");

		driver.findElement(By.id("form-maternal")).sendKeys("Salamanca");

		Date now = new Date();
		String email = "isantos" + today.getYear() + today.getMonthValue() + today.getDayOfMonth() + today.getHour() + today.getMinute()
				+ "@mcfadyen.com";
		/*String email = "isantos" + now .getYear() + now.getMonth() + now.getDay() + now.getHours() + now.getMinutes()
		+ "@gruponazan.com";
		 email = "isantos@gruponazan.com";*/


		driver.findElement(By.id("form-email")).sendKeys(email);
		System.out.println(email);
		logger.info(email);
		driver.findElement(By.id("register_password")).sendKeys("asdfqwer");

		driver.findElement(
				By.cssSelector("#frmregister > div.form-content > div.form-group.col-md-12 > #confirmpassword"))
				.sendKeys("asdfqwer");

		driver.findElement(By.id("inlineRadio2")).click();

		driver.findElement(By.id("SendRegisterButton")).click();
		Thread.sleep(3000);

		driver.get(baseUrl2 + "/dyn/admin/");
		Thread.sleep(5000);
		driver.findElement(By.linkText("Component Browser")).click();
		driver.findElement(By.linkText("Search")).click();
		driver.findElement(By.id("searchField")).clear();
		driver.findElement(By.id("searchField")).sendKeys("ProfileAdapterRepository");
		driver.findElement(By.id("searchField")).sendKeys(Keys.RETURN);
		Thread.sleep(3000);
		driver.findElement(By.linkText("/atg/userprofiling/ProfileAdapterRepository")).click();
		Thread.sleep(3000);
		driver.findElement(By.name("xmltext")).clear();

		driver.findElement(By.name("xmltext")).sendKeys(
				"<query-items item-descriptor=\"user\">email = \"" + email + "\"</query-items>");
		
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		
		String s = ((driver.findElement(By.cssSelector("code")).getText()));
		s = s.substring(s.indexOf('\n')+1).replaceAll("\n", "");
		String retorno = getTokenFromXML("<root>"+s+"</root>");
		
		System.out.println(retorno);
		logger.info(retorno);
		driver.get(baseUrl + "/impuls/store/home?token="+retorno+"&activateModal=true");
		driver.findElement(By.id("memberemail")).sendKeys(email);
		
		driver.findElement(By.id("password")).sendKeys("asdfqwer");
		driver.findElement(By.name("/atg/userprofiling/ProfileFormHandler.login")).click();
		
	
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
	
	public static String getTokenFromXML(String xml){
		  try{
			  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputStream stream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
				Document doc = db.parse(stream);

				Element raiz = doc.getDocumentElement();

				NodeList item = raiz.getElementsByTagName("add-item");
				item.item(0).getChildNodes().item(1).getTextContent();
				NodeList list = item.item(0).getChildNodes();
				String token = "";
				for (int i = 0; i < list.getLength(); i++) {
					if (list.item(i).getAttributes() != null) {
						NamedNodeMap attr = list.item(i).getAttributes();
						if (attr.getNamedItem("name").getTextContent().equals("verificationToken")) {
							token = list.item(i).getTextContent();
						}
					}

				}
				return token;
		  }
		  catch (ParserConfigurationException e) {
					
					e.printStackTrace();
				} catch (SAXException e) {
					
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
		  return "";

		 }
}
