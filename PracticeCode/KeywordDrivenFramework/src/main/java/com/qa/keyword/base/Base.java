package com.qa.keyword.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Base {

	public static WebDriver driver;
	public static Properties prop;

	public static WebDriver init_driver(String browsername) throws IOException {
		String path = new File(".").getCanonicalPath() + "\\chromedriver.exe";
		if (browsername.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", path);
			driver = new ChromeDriver();
		}
		return driver;

	}

	public static Properties init_properties() throws IOException {
		String proppath = new File(".").getCanonicalPath()
				+ "//src//main//java//com//qa//keyword//config//config.properties";
		FileInputStream fis = new FileInputStream(proppath);
		prop = new Properties();
		prop.load(fis);
		return prop;
	}
}
