package com.qa.keyword.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class KeywordAction {

	public void enter_text(WebDriver driver, By xpath, String data) {

		driver.findElement(xpath).sendKeys(data);

	}

	public void click(WebDriver driver, By xpath, String data) {

		driver.findElement(xpath).click();

	}

}
