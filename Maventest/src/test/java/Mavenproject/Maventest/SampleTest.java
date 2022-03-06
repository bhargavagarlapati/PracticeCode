package Mavenproject.Maventest;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class SampleTest {
	
	@Test
	public void m()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\GARREDDY\\Documents\\Selenium\\chromedriver.exe");

		WebDriver driver = new ChromeDriver();

		driver.get("https://www.amazon.com/");
		
		driver.quit();
		
		assertTrue(false);
		
		
	}

}
