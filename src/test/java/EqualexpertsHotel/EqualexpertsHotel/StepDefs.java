package EqualexpertsHotel.EqualexpertsHotel;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Array;
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
	String[] fieldDescGrid = new String[]{ // gui element array, in tab order
			"Firstname=//input[@id='firstname']",
			"Surname=//input[@id='lastname']",
			"Price=//input[@id='totalprice']",
			"Deposit=//select[@id='depositpaid']",
			"CheckIn=//input[@id='checkin']",
			"CheckOut=//input[@id='checkout']"
			};
	public static HashMap<String,String> AssertionGrid;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	//
	@Given("^I am on the hotel booking homepage$")
	public void user_is_on_Home_Page() throws Throwable {
		//WebDriver driver;
		//String osSystem = 
		CoreFunctions.browserLaunch("Chrome","http://hotel-test.equalexperts.io/");
		}

	@Given("^I create a new booking with the following data$")
	public void i_have_entered_computer_details(DataTable inputData) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	
		List<Map<String,String>> data = inputData.asMaps(String.class,String.class);
				
		AssertionGrid = CoreFunctions.AUTFormInput(fieldDescGrid, data);
        //CoreFunctions.AUTFormInput(fieldDescGrid, data); // handle data input
	   // System.out.println("Assertiongrid = " + AssertionGrid);
		
		Firstname = AssertionGrid.get("Firstname");
		Surname = AssertionGrid.get("Surname");
		Price = AssertionGrid.get("Price");
		Deposit = AssertionGrid.get("Deposit");
		CheckIn = AssertionGrid.get("CheckIn");	
		CheckOut = AssertionGrid.get("CheckOut");	
		
		System.out.println(Firstname + " " + Surname);
		
	}
	
	@When("^I click the Save button$")
	public void i_click_the_Save_button() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		CoreFunctions.driver.findElement(By.xpath("//input[@value=' Save ']")).click();
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
		//Firstname = CoreFunctions.AssertionGrid.get(Firstname);
		String displayRow = CoreFunctions.gridInterrogation("//div",Firstname); // look for the first name in the grid, return the appropriate row for assertion.
		
		String DisplayedFirstName = CoreFunctions.driver.findElement(By.xpath("//div[@id='" + displayRow + "']/div/p")).getText();
		assertEquals(Firstname, DisplayedFirstName);
		
		String DisplayedSurname = CoreFunctions.driver.findElement(By.xpath("//div[@id='" + displayRow + "']/div[2]/p")).getText();
		assertEquals(Surname, DisplayedSurname);
		
		String DisplayedPrice = CoreFunctions.driver.findElement(By.xpath("//div[@id='" + displayRow + "']/div[3]/p")).getText();
		assertEquals(Price, DisplayedPrice);
		
		String DisplayedDeposit = CoreFunctions.driver.findElement(By.xpath("//div[@id='" + displayRow + "']/div[4]/p")).getText();
		assertEquals(Deposit, DisplayedDeposit);
		
		String DisplayedCheckIn = CoreFunctions.driver.findElement(By.xpath("//div[@id='" + displayRow + "']/div[5]/p")).getText();
		assertEquals(CheckIn, DisplayedCheckIn);
		
		String DisplayedCheckOut = CoreFunctions.driver.findElement(By.xpath("//div[@id='" + displayRow + "']/div[6]/p")).getText();
		assertEquals(CheckOut, DisplayedCheckOut);
		
	}

	@And("^I close the driver instance$")
	public void I_close_the_driver_instance() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new PendingException();
		CoreFunctions.driver.quit();
		
		
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
	    
		System.out.println("searching for row using string of " + Firstname);
		String displayRowID = CoreFunctions.gridInterrogation("//div",Firstname); 
		// this gives us the row to delete. It would be better to get the ID number of the record, as this will guarantee identification, but not enough time!
		System.out.println("scenario record for deletion found, row ID " + displayRowID);
		
		// the delete button is on a row, but the first row contains table headers, so subtract 1 from the returned row for the TRUE occurance index
		// ... until I've worked out how to extract the iterative (exclusive) row index
		
		//displayRowInt = displayRowInt-1;
		//div[@id='26865']/div[7]/input
		
		
		//div[@id='26845']/div[7]/input
		String DeleteButton = "//div[@id='" + displayRowID + "']/div[7]/input";
		
		CoreFunctions.driver.findElement(By.xpath(DeleteButton)).click();
		
	}
	
	@Then("^My booking is deleted$")
	public void my_booking_is_deleted() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		String displayRowID = CoreFunctions.gridInterrogation("//div",Firstname); 
		
		assertEquals(displayRowID, "matching row not found"); // once deleted, booking should not be displayed.

	}
	
}

