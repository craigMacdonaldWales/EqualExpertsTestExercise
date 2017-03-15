package EqualexpertsHotel.EqualexpertsHotel;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.ErrorCodes;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CoreFunctions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String gridInterrogation(String searchCriteria, String queryIndex){ // with more time I'd expand this function to process column by column, row by row. or pass in a column to interrogate
	
	//Number displayRowCount = StepDefs.driver.findElements(By.xpath("(//input[@value='Delete'])")).size(); // hooks - each row has a delete button. Count the occurances.
	//System.out.println("Grid interrogation - Number of Rows found in booking display: " + displayRowCount);
	
	// first row contains headers. True index for field data is rowcount+1.
	
	List<WebElement> el = StepDefs.driver.findElements(By.xpath(searchCriteria));
	System.out.println("number of matching elements found = " +  el.size());
	
	String out;
	String id = null;
	String rowId = null;
	int i = 1;
	for (i = 1; i<=el.size(); i=i+1)
	{
		
		try{
		
			out = el.get(i).getText();
			id = el.get(i).getAttribute("id");
		
		} catch (StaleElementReferenceException e){
			
			System.out.println("number of loop iterations = " + i + ", search string " + queryIndex + " not found");
			return "matching row not found";
		}
		
		switch (id){
		case "":
			break;
		default:
			//System.out.println("Row ID  = " + id);
			rowId = id;
			break;
		}
		
		//System.out.println("text  = " + out + "associated with id / row " + rowId);
		
		if (out.equals(queryIndex)){
			// we've found the string. return the rowId for use in descriptors
			System.out.println("Query text found in display, associated with ID: " + rowId);
			return rowId;
		}
		
		
	}
	
	System.out.println("number of loop iterations = " + i + ", search string " + queryIndex + " not found");
	return "matching row not found";

	}

	
	public static String postSaveResponsePolling (){
		
		String response;
		int displayRowCount = StepDefs.driver.findElements(By.xpath("(//input[@value='Delete'])")).size(); // get the rowcount pre-save
		int displayRowCountExp = (displayRowCount+1);
		String displayRowCountExpString = String.valueOf(displayRowCountExp);
				
		String SaveSyncElement = "(//input[@value='Delete'])[" + displayRowCountExpString + "]";
		
		System.out.println("Synchronization - waiting for element " + SaveSyncElement);
		
		//xpath=(//input[@value='Delete'])[8]
		
		try{
		WebElement element = (new WebDriverWait(StepDefs.driver, 20)) // wait for the new delete button (and therefore, new row)
				   .until(ExpectedConditions.elementToBeClickable(By.xpath(SaveSyncElement)));
		//throw new PendingException();
		} catch (TimeoutException e){
			//e.printStackTrace();
			System.out.println("new record row not detected within timeout");
			response = "false";
			return response;
		}
		
		response = "true";
		return response;
		
	}
	
	
}
