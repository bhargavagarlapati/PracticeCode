package com.qa.keyword.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import com.qa.keyword.base.Base;

public class ExecutionEngine extends Base {
	public static List<String> keywords, locators, inputdata;
	public static String path;
	public static Method[] methods;
    public static KeywordAction keywordaction;
	public static void main(String[] args) {

		try {
			path = new File(".").getCanonicalPath() + "\\Keyword_TC1.xlsx";
		} catch (IOException e) {
			e.printStackTrace();
		}
        try {
			Base.init_driver("Chrome");
			Base.init_properties();
		} catch (IOException e) {			
			e.printStackTrace();
		}
        driver.get(prop.getProperty("url"));
        driver.manage().window().maximize();
		ExcelReader er = new ExcelReader();
		
		Map<String, List<String>> m = er.dataFetcher(path);

		keywords = m.get("Keyword");
		locators = m.get("ObjectID");
		inputdata = m.get("KeyInData");
		keywordaction = new KeywordAction();
		methods = keywordaction.getClass().getMethods();
		Util U = new Util();

		for (int i = 0; i < keywords.size(); i++) {

			By xpath = U.xpathLocator(locators.get(i));
			String data = inputdata.get(i);
			String keyword = keywords.get(i);
			executeKeyword(keyword, xpath, data);
		}

	}

	public static void executeKeyword(String keyword, By xpath, String data) {
		for (int i = 0;i<methods.length;i++) {
			if (methods[i].getName().equalsIgnoreCase(keyword)) {
				try {
					methods[i].invoke(keywordaction, driver, xpath, data);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
