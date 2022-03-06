package com.qa.keyword.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.openqa.selenium.By;

public class Util {
	Properties prop;
	FileInputStream fis;
	String path;

	Util() {
		try {
			path = new File(".").getCanonicalPath()
					+ "\\src\\main\\java\\com\\qa\\keyword\\config\\Object_repository.properties";
			fis = new FileInputStream(path);
			prop = new Properties();
			prop.load(fis);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public By xpathLocator(String objectid) {

		String xpath = prop.getProperty(objectid);
		By by = By.xpath(xpath);
		return by;
	}

}
