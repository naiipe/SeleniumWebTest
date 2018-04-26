package com.gn.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLDynTest {

	@Test
	public void test() {

		String samba = "<root><add-item item-descriptor=\"user\" id=\"5810001\">  <set-property name=\"middleName\"><![CDATA[Salamanca]]></set-property>  <set-property name=\"firstLogin\"><![CDATA[true]]></set-property>  <set-property name=\"email\"><![CDATA[isantos11762113@gruponazan.com]]></set-property>  <set-property name=\"password\"><![CDATA[1eebd7170bc8e68835b593b1ee53799b061b81f8e74947a054106bd2a61b20a5a4c86956d1e312815cfe3b4fc96ced567306a76219d7b52da48fc15ba1963afd]]></set-property>  <set-property name=\"verificationTokenCreation\"><![CDATA[7/25/2017 19:34:43]]></set-property>  <!-- rdonly  derived   <set-property name=\"abandonedOrderCount\"><![CDATA[0]]></set-property>  -->  <set-property name=\"locale\"><![CDATA[es_MX]]></set-property>  <set-property name=\"lastName\"><![CDATA[Filipe]]></set-property>  <set-property name=\"registrationDate\"><![CDATA[7/25/2017 19:34:43]]></set-property>  <set-property name=\"passwordKeyDerivationFunction\"><![CDATA[6]]></set-property>  <set-property name=\"verificationToken\"><![CDATA[2f542c28-692f-4cfe-9861-2b36c861c1fb]]></set-property>  <set-property name=\"memberType\"><![CDATA[silver]]></set-property>  <set-property name=\"wishlist\"><![CDATA[gl5710002]]></set-property>  <set-property name=\"login\"><![CDATA[isantos11762113@gruponazan.com]]></set-property>  <set-property name=\"lastActivity\"><![CDATA[7/25/2017 19:34:43]]></set-property>  <set-property name=\"homeAddress\"><![CDATA[5810001]]></set-property>  <set-property name=\"passwordSalt\"><![CDATA[96f58b2b2f33c9c7bd1ac4b4388eb346721c090b]]></set-property>  <set-property name=\"firstName\"><![CDATA[Ivan]]></set-property>  <set-property name=\"lastPasswordUpdate\"><![CDATA[7/25/2017 19:34:42]]></set-property>  <set-property name=\"renewing\"><![CDATA[false]]></set-property>  <!-- rdonly   <set-property name=\"GoldMembership\"><![CDATA[false]]></set-property>  -->  <!-- rdonly   <set-property name=\"basic\"><![CDATA[false]]></set-property>  -->  <!-- rdonly   <set-property name=\"Gold\"><![CDATA[false]]></set-property>  --></add-item></root>";

		/*
		 * samba = samba.replace("<]]", ""); samba = samba.replace("<![CDATA[",
		 * "");
		 */
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream stream = new ByteArrayInputStream(samba.getBytes(StandardCharsets.UTF_8));
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
			assertEquals("2f542c28-692f-4cfe-9861-2b36c861c1fb", token);
		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		} catch (SAXException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
