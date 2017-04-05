package EqualexpertsHotel.EqualexpertsHotel;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import com.google.common.base.Function;

import pageObjects.bookingDisplay;

public class CoreFunctions {

	public static WebDriver driver;
	public static String osSystem = System.getProperty("os.name");
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String InputDataManipulation(String field, String data){
		// identify particular fields and manipulate the data so an end user doesn't have to.
		boolean dateMarker = data.contains("today");
		
		if (dateMarker){
			data = dataUtils.dateprep(data); // handle the date.
			return data;
		}
		// add specialist behaviours for specific fields here. This was originally a switch statement as the options might expand,
		// and I'd want to retain the structure of this handling behaviour with a cross-project class
		if (field.equals("Firstname")){
	
			int milliAppend = dataUtils.getTimeInt();
			String appendString = Integer.toString(milliAppend);
			data = data + appendString;
			return data;
		}
		
		return data;
	}
	
	public static void browserLaunch (String browserType, String targetURL){

		System.out.println(osSystem);
		
		driver = driverHandle();
		
		driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    driver.get(targetURL); //goto the page
	
	}
	
	public static WebElement getRowUsingXpath (String cellValueToFind){ 
		
		WebElement targetElement = null;		
		String hashRow;
		String xpathSearch = "//*[text() = '" + cellValueToFind + "']/../..";
		
		targetElement = waitPolling(xpathSearch);
		
		if (targetElement != null){
			// get the row index for use with CSS descriptor (idHash)
			hashRow = targetElement.getAttribute("id");
			System.out.println("Target element found, hashrow = " + hashRow);
			bookingDisplay.setIdHash(hashRow); // set the ID for use in the bookingDisplay page object

		}
		
		return targetElement;
	}

	private static WebElement waitPolling(final String elementDesc) {
				
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
		.withTimeout(10000, TimeUnit.MILLISECONDS)
		.pollingEvery(100, TimeUnit.MILLISECONDS)
		.ignoring(NoSuchElementException.class);

		WebElement elementWait = wait.until(new Function<WebDriver, WebElement>() {
		public WebElement apply(WebDriver driver) {
			return driver.findElement(By.xpath(elementDesc));
			}
		});
		
		return elementWait;
	}
	
	private static WebDriver driverHandle(){
			
		String browserType = System.getProperty("browsertype", "Chrome"); // so we can still debug in the IDE
	
		System.out.println("browsertype passed in:" + browserType);
	
		
		if (browserType.isEmpty()){ // still empty
			//browserType = "Chrome"; // default to Chrome.		
		}else{
			System.out.println("Browser type declared from command line = " + browserType);
		}
		
		
		String chromeDriverPath = System.getProperty("webdriver.chrome.driver");
		if (StringUtils.isEmpty(chromeDriverPath)){ // only set if not null. Do not override.
			System.out.println("chromedriver path property not found, path will be set.");
		
			if (osSystem.equals("Mac OS X")){
				System.setProperty("webdriver.chrome.driver", "//chromedriver//chromedriver");
			}else{
				System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe");
			}	
		
		}
			
		String firefoxDriverPath = System.getProperty("webdriver.gecko.driver");
		if (StringUtils.isEmpty(firefoxDriverPath)){ // only set if not null. Do not override.
			System.out.println("firefox driver path property not found, path will be set");
		
			if (osSystem.equals("Mac OS X")){
				System.setProperty("webdriver.gecko.driver", "//geckodriver//geckodriver");
			}else{
				System.setProperty("webdriver.gecko.driver", "C:\\GeckoDriver\\geckodriver.exe");
			}	
		
		}
		
		switch (browserType){
			case "Firefox":
				driver = new FirefoxDriver();
				break;
			case "Chrome":	
				driver = new ChromeDriver();
				break;
		}
		
		return driver;
	}
}
