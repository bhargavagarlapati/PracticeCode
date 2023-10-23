package Docker.Docker_Grid_Test;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;

public class GridTest {

	@Test
	public void m1() throws MalformedURLException

	{

		RemoteWebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), new ChromeOptions());

		driver.get("https://www.amazon.in/");

		System.out.print(driver.getTitle());

		driver.quit();

	}

}
