package EqualexpertsHotel.EqualexpertsHotel;


import static org.junit.Assert.assertEquals;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.WebElement;
import EqualexpertsHotel.EqualexpertsHotel.dataUtils;
import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

//import taf.ActorLibrary;
//import taf.OperationStepProcess;
//import taf.SpineReturn;

import pageObjects.bookingDisplay;


public class StepDefs{
	
	public static HashMap<String,String> scenarioInfoContainer;
	public static String Browser;
	public static String Firstname;
	public static String Surname;
	public static String Price;
	public static String Deposit;
	public static String CheckIn;
	public static String CheckOut;
	public static Number displayRowCount;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	//
	@Given("^I am on the hotel booking homepage$")
	public void user_is_on_Home_Page() throws Throwable {
			
		CoreFunctions.browserLaunch(Browser,"http://hotel-test.equalexperts.io/");
		}

	@Given("^I create a new booking with the following data$")
	public static void i_have_entered_computer_details(DataTable inputData) throws Throwable {
	
		List<Map<String,String>> data = inputData.asMaps(String.class,String.class);
	
		int appendString = dataUtils.getTimeInt();
		// mapping & console output	
		Firstname = (data.get(0).get("Firstname") + appendString);
		
		bookingDisplay.editBoxFirstname(CoreFunctions.driver).sendKeys(Firstname);
		Surname = data.get(0).get("Surname");
		System.out.println(Surname);
		bookingDisplay.editBoxSurname(CoreFunctions.driver).sendKeys(Surname);
		
		Price = data.get(0).get("Price");
		System.out.println(Price);
		bookingDisplay.editBoxPrice(CoreFunctions.driver).sendKeys(Price);
		
		Deposit = data.get(0).get("Deposit");
		System.out.println(Deposit);
		bookingDisplay.selectDeposit(CoreFunctions.driver).sendKeys(Deposit);
		
		CheckIn = data.get(0).get("CheckIn");
		CheckIn = dataUtils.dateprep(CheckIn); // call code to handle date prep here here
		System.out.println(CheckIn);
		bookingDisplay.editCheckInDate(CoreFunctions.driver).sendKeys(CheckIn);
		
		CheckOut = data.get(0).get("CheckOut");
		CheckOut = dataUtils.dateprep(CheckOut);
		System.out.println(CheckOut);
		bookingDisplay.editCheckOutDate(CoreFunctions.driver).sendKeys(CheckOut);

		
	}
	
	@When("^I click the Save button$")
	public void i_click_the_Save_button() throws Throwable {
		bookingDisplay.buttonSave(CoreFunctions.driver).click();
	}
	
	@Then("^My booking is created$")
	public void My_booking_is_created() throws Throwable {
	   
		String lineSep = System.lineSeparator();
		String createdBookingContent;
		String ExpectedBookingContent = Firstname + lineSep + Surname + lineSep + Price + lineSep + Deposit + lineSep + CheckIn + lineSep + CheckOut;
		// the presence of the row for the booking is confirmation that the save operation has worked, using the unique reference (firstName).
		// the POM calls the coreFunction (waitPolling) to find the presence of the new row (max wait 10 seconds).
		WebElement createdBooking = bookingDisplay.bookingDisplayElementFind(CoreFunctions.driver, Firstname);
		
		createdBookingContent = createdBooking.getText();
		
		assertEquals(ExpectedBookingContent,createdBookingContent);
		
	}

	@Then("^Creation of the new record is rejected$")
	public void creation_of_the_new_record_is_rejected() throws Throwable {
		Thread.sleep(1000);
		bookingDisplay.buttonSave(CoreFunctions.driver).click(); // save button will still be there, and clickable.
		
	}
	
	@Then("^I click the Delete button$")
	public void i_click_the_Delete_button() throws Throwable {

	    bookingDisplay.buttonDelete(CoreFunctions.driver).click();
		
	}
	
	@Then("^My booking is deleted$")
	public void my_booking_is_deleted() throws Throwable {
		bookingDisplay.assertDeleteNotPresent(CoreFunctions.driver);
	}
	
	@After
	public void afterScenario() {
	    CoreFunctions.driver.quit();
	}
	
}

