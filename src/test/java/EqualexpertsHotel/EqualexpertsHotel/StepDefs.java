package EqualexpertsHotel.EqualexpertsHotel;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import EqualexpertsHotel.EqualexpertsHotel.dataUtils;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

//import taf.ActorLibrary;
//import taf.OperationStepProcess;
//import taf.SpineReturn;

public class StepDefs{

	public static WebDriver driver;
	public static String osSystem = System.getProperty("os.name");
	//public static String Name;
	public static HashMap<String,String> scenarioInfoContainer;
	public static String Browser;
	public static String Firstname;
	public static String Surname;
	public static String Price;
	public static String Deposit;
	public static String CheckIn;
	public static String CheckOut;
	public static Number displayRowCount;
	// AUT input entities
	
	public static HashMap<String,String> fieldDescGrid;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	//
	@Given("^I am on the hotel booking homepage$")
	public void user_is_on_Home_Page() throws Throwable {
		//WebDriver driver;
		//String osSystem = 
	
		Browser = "Chrome";
		//Browser = "Firefox";
		
		//ActorLibrary.scenarioExecution(1);
		
		System.out.println(osSystem);
		
		
		switch (osSystem){
			case "Mac OS X":	
	
				switch (Browser){
					case "Firefox":
						System.setProperty("webdriver.gecko.driver", "//geckodriver//geckodriver"); // need to document this
						driver = new FirefoxDriver();
						break;
					case "Chrome":	
						System.setProperty("webdriver.chrome.driver", "//chromedriver//chromedriver"); // need to document this
						driver = new ChromeDriver();
						break;
					default:
						System.setProperty("webdriver.gecko.driver", "//geckodriver//geckodriver"); // need to document this
						driver = new FirefoxDriver();
						break;
				}
					
				break; // end of mac os options
					
			default: // windows
				switch (Browser){
					case "Firefox":
						System.setProperty("webdriver.gecko.driver", "C:\\GeckoDriver\\geckodriver.exe"); // need to document this
						driver = new FirefoxDriver();
						break;
					case "Chrome":	
						System.setProperty("webdriver.chrome.driver", "C:\\chromedriver_win32\\chromedriver.exe"); // need to document this
						driver = new ChromeDriver();
						break;
					default:
						System.setProperty("webdriver.gecko.driver", "C:\\GeckoDriver\\geckodriver.exe"); // need to document this
						driver = new FirefoxDriver();
						break;			
					}
			}
		
		
		driver.manage().window().maximize();
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    driver.get("http://hotel-test.equalexperts.io/"); //goto the page
	      
		}

	@Given("^I create a new booking with the following data$")
	public void i_have_entered_computer_details(DataTable inputData) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		
		// GUI descriptors here - centralised maintenance
		//fieldDescGrid.put("Firstname","//input[@id='firstname']");
		//fieldDescGrid.put("Surname","//input[@id='lastname']");
		//fieldDescGrid.put("Price","//input[@id='totalprice']");
		//fieldDescGrid.put("Deposit","//select[@id='depositpaid']");
		//fieldDescGrid.put("CheckIn","//input[@id='checkin']");
		//fieldDescGrid.put("CheckOut","//input[@id='checkout']");
		// this will only work if the GUI hashmap and inputData list use the same references (case sensitive)
		// is it worth lower-casing the field references?
		
		List<Map<String,String>> data = inputData.asMaps(String.class,String.class);
		
		//CoreFunctions.AUTFormInput(fieldDescGrid, data); // handle data input
		
		
		
		int milliAppend = dataUtils.getTimeInt();
		String appendString = Integer.toString(milliAppend);
		// mapping & console output	
		Firstname = (data.get(0).get("Firstname") + appendString);
		System.out.println(Firstname);
		Surname = data.get(0).get("Surname");
		System.out.println(Surname);
		Price = data.get(0).get("Price");
		System.out.println(Price);
		Deposit = data.get(0).get("Deposit");
		System.out.println(Deposit);
		
		CheckIn = data.get(0).get("CheckIn");
		CheckIn = dataUtils.dateprep(CheckIn); // call code to handle date prep here here
		System.out.println(CheckIn);
		
		CheckOut = data.get(0).get("CheckOut");
		CheckOut = dataUtils.dateprep(CheckOut);
		System.out.println(CheckOut);
	
		driver.findElement(By.xpath("//input[@id='firstname']")).sendKeys(Firstname);
		driver.findElement(By.xpath("//input[@id='lastname']")).sendKeys(Surname);
		driver.findElement(By.xpath("//input[@id='totalprice']")).sendKeys(Price);
	   // driver.findElement(By.xpath("//select[@id='depositpaid']")).click();
	    //driver.wait();
		    
	    switch (osSystem){ // select behaviours - variance neccesary?
		case "Mac OS X":	
			 // need to document this
			//Thread.sleep(1000);
			//new Select(driver.findElement(By.xpath("//select[@id='depositpaid']"))).selectByVisibleText("false");
			//driver.findElement(By.xpath("//select[@id='depositpaid']")).click();
			driver.findElement(By.xpath("//select[@id='depositpaid']")).sendKeys(Deposit);
			
			break;
		default:
			System.out.println("windows list select");
			//driver.findElement(By.xpath("//select[@id='depositpaid']")).click();
			driver.findElement(By.xpath("//select[@id='depositpaid']")).sendKeys(Deposit);
			break;
	    }
	
	    driver.findElement(By.xpath("//input[@id='checkin']")).sendKeys(CheckIn);
		driver.findElement(By.xpath("//input[@id='checkout']")).sendKeys(CheckOut);
	    
	}
	
	@When("^I click the Save button$")
	public void i_click_the_Save_button() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		driver.findElement(By.xpath("//input[@value=' Save ']")).click();
		// it would be nice to package this up into a generic wait method in a core class
	}
	
	@Then("^My booking is created$")
	public void My_booking_is_created() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		
		String saveResponse = CoreFunctions.postSaveResponsePolling();
		
		//String displayRow = CoreFunctions.gridInterrogation("//div", Firstname); 
		assertEquals(saveResponse, "true");
		//function to interrogate table and extract row using name field as index.
		
		String displayRow = CoreFunctions.gridInterrogation("//div",Firstname); // look for the first name in the grid, return the appropriate row for assertion.
		
		String DisplayedFirstName = StepDefs.driver.findElement(By.xpath("//div[@id='" + displayRow + "']/div/p")).getText();
		assertEquals(Firstname, DisplayedFirstName);
		
		String DisplayedSurname = StepDefs.driver.findElement(By.xpath("//div[@id='" + displayRow + "']/div[2]/p")).getText();
		assertEquals(Surname, DisplayedSurname);
		
		String DisplayedPrice = StepDefs.driver.findElement(By.xpath("//div[@id='" + displayRow + "']/div[3]/p")).getText();
		assertEquals(Price, DisplayedPrice);
		
		String DisplayedDeposit = StepDefs.driver.findElement(By.xpath("//div[@id='" + displayRow + "']/div[4]/p")).getText();
		assertEquals(Deposit, DisplayedDeposit);
		
		String DisplayedCheckIn = StepDefs.driver.findElement(By.xpath("//div[@id='" + displayRow + "']/div[5]/p")).getText();
		assertEquals(CheckIn, DisplayedCheckIn);
		
		String DisplayedCheckOut = StepDefs.driver.findElement(By.xpath("//div[@id='" + displayRow + "']/div[6]/p")).getText();
		assertEquals(CheckOut, DisplayedCheckOut);
		
	}

	@And("^I close the driver instance$")
	public void I_close_the_driver_instance() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		driver.quit();
		
		
	}

	@Then("^Creation of the new record is rejected$")
	public void creation_of_the_new_record_is_rejected() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		String saveResponse = CoreFunctions.postSaveResponsePolling();
		assertEquals(saveResponse, "false"); // the new row isn't found. Return false
		
	}
	
	@Then("^I click the Delete button$")
	public void i_click_the_Delete_button() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    
		String displayRowID = CoreFunctions.gridInterrogation("//div",Firstname); 
		// this gives us the row to delete. It would be better to get the ID number of the record, as this will guarantee identification, but not enough time!
		System.out.println("scenario record for deletion found, row ID " + displayRowID);
		
		// the delete button is on a row, but the first row contains table headers, so subtract 1 from the returned row for the TRUE occurance index
		// ... until I've worked out how to extract the iterative (exclusive) row index
		
		//displayRowInt = displayRowInt-1;
		//div[@id='26865']/div[7]/input
		
		
		//div[@id='26845']/div[7]/input
		String DeleteButton = "//div[@id='" + displayRowID + "']/div[7]/input";
		
		driver.findElement(By.xpath(DeleteButton)).click();
		
	}
	
	@Then("^My booking is deleted$")
	public void my_booking_is_deleted() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		String displayRowID = CoreFunctions.gridInterrogation("//div",Firstname); 
		
		assertEquals(displayRowID, "matching row not found"); // once deleted, booking should not be displayed.

	}
	
}

