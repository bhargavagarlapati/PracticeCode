package Docker.Docker_Grid_Test;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class GridTwoTest {

	@Test
	public void m2() throws MalformedURLException

	{

		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), new ChromeOptions());

		driver.get("https://www.google.com/");

		System.out.print(driver.getTitle());

		driver.quit();

	}

}
