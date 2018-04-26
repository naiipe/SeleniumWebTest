package com.gn.tests;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GNAbstractTest {
	final static Logger logger = Logger.getLogger(GNAbstractTest.class);
	public WebDriver driver;
	public String baseUrl;
	public boolean acceptNextAlert = true;
	public StringBuffer verificationErrors = new StringBuffer();
	Properties p = new Properties();
	

	public void setUp() throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();
		classLoader.getResource("chromedriver").getFile();

		System.setProperty("webdriver.chrome.driver", classLoader.getResource("chromedriver").getFile());
		
		p.load(new FileReader(classLoader.getResource("config.properties").getFile()));
		baseUrl = p.getProperty("config.urlbase");
		driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public static String getTokenFromXML(String xml, String setPropertyName) throws Exception {
		try {
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
					if (attr.getNamedItem("name").getTextContent().equals(setPropertyName)) {
						token = list.item(i).getTextContent();
					}
				}

			}
			if(token.isEmpty())
				throw new Exception("Property not found");
			return token;
		} catch (ParserConfigurationException e) {

			throw new Exception("Reading error");
		} catch (SAXException e) {

			throw new Exception("Reading error");
		} catch (IOException e) {

			throw new Exception("Reading error");
		}
		

	}

}
