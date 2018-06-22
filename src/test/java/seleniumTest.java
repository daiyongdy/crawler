import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by daiyong on 2018/6/5.
 * email daiyong@qiyi.com
 */
public class seleniumTest {

	@Test
	public void seleniumTest(){
		System.getProperties().setProperty("webdriver.chrome.driver", "/Users/daiyong/Downloads/chromedriver");
		WebDriver webDriver = new ChromeDriver();
		webDriver.get("https://twitter.com/ethereum");
		WebElement webElement = webDriver.findElement(By.xpath("/html"));
		System.out.println(webElement.getAttribute("outerHTML"));
		webDriver.close();
	}

}
