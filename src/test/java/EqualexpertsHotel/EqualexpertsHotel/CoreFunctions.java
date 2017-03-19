package EqualexpertsHotel.EqualexpertsHotel;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.ErrorCodes;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CoreFunctions {

	public static WebDriver driver;
	public static String osSystem = System.getProperty("os.name");
	//public static HashMap<String,String> AssertionGrid = new HashMap(); // create a hashmap to store the data for post input assertions
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String gridInterrogation(String searchCriteria, String queryIndex){ // with more time I'd expand this function to process column by column, row by row. or pass in a column to interrogate
	
	//Number displayRowCount = StepDefs.driver.findElements(By.xpath("(//input[@value='Delete'])")).size(); // hooks - each row has a delete button. Count the occurances.
	//System.out.println("Grid interrogation - Number of Rows found in booking display: " + displayRowCount);
	
	// first row contains headers. True index for field data is rowcount+1.
	
	List<WebElement> el = driver.findElements(By.xpath(searchCriteria));
	System.out.println("number of matching elements found = " +  el.size());
	
	String out;
	String id = null;
	String rowId = null;
	int i = 1;
	for (i = 0; i<el.size(); i=i+1)
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
		int displayRowCount = driver.findElements(By.xpath("(//input[@value='Delete'])")).size(); // get the rowcount pre-save
		int displayRowCountExp = (displayRowCount+1);
		String displayRowCountExpString = String.valueOf(displayRowCountExp);
				
		String SaveSyncElement = "(//input[@value='Delete'])[" + displayRowCountExpString + "]";
		
		System.out.println("Synchronization - waiting for element " + SaveSyncElement);
		
		//xpath=(//input[@value='Delete'])[8]
		
		try{
		WebElement element = (new WebDriverWait(driver, 20)) // wait for the new delete button (and therefore, new row)
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
	
	public static HashMap AUTFormInput (String[] GUI, List inputValues){

		System.out.println("AUT form input");
		HashMap<String,String> AssertionGrid = new HashMap(); 
		Object data;
		String dataString;

        data = inputValues.get(0).toString();
        dataString = data.toString();
        
        dataString = dataString.replaceAll("[{}]", ""); // tidy up the arraylist passed in by cucumber.
    
        String dataSplit[] = dataString.split(", ");
        String valSplit[];
        
        HashMap<String,String> dataGrid = new HashMap(); // create a hashmap to store the data.
        
        int dataTreat = dataSplit.length;
        String dataTreated;
        for (String o : dataSplit){ // fill the hashmap, using field name as the index
            System.out.println(o);
            valSplit = o.split("=");          
            // deal with trigger values here
            dataTreated = InputDataManipulation(valSplit[0], valSplit[1]);      
            
            dataGrid.put(valSplit[0], dataTreated);  
         }
        
		//Object key;
		String keyString;

		String element;
		String GUISplit[];
		for(int i = 0; i < GUI.length; i++) // link up the GUI items (element descriptors) and the data using field name as the common key.
		{
		   element = GUI[i];
		    //String nextElement = elements[i+1];
		   GUISplit = element.split("=//");  
	       
		   keyString = GUISplit[0];
		   element = "//" + GUISplit[1];
	       dataString = dataGrid.get(keyString);
	    
	       System.out.println("Field = " + keyString + ", element ID = " + element + ", Data value to send to AUT = " + dataString);
	      
	       // fire in the data.
	       fieldSend(element,dataString);
	       
	       AssertionGrid.put(keyString, dataString);
	       
	       
	    }
		//System.out.println(AssertionGrid);

		return AssertionGrid;
	}
	
	public static String InputDataManipulation(String field, String data){
		// not as neat as I would like!
		// identify particular fields and manipulate the data so an end user doesn't have to.
		boolean dateMarker = data.contains("today");
		
		if (dateMarker){
			data = dataUtils.dateprep(data); // handle the date.
			return data;
		}
		// add specialist behaviours for specific fields here.
		switch (field){
		case "Firstname":
			int milliAppend = dataUtils.getTimeInt();
			String appendString = Integer.toString(milliAppend);
			data = data + appendString;
			
			//StepDefs.Firstname = data;
			break;
		default:
			
			break;
		}
		
		return data;
	}
	
	public static void fieldSend (String elementID, String inputData){
		
		// switch for field type?
		// adaptations for xpath, CSS?
		driver.findElement(By.xpath(elementID)).sendKeys(inputData);
		
	}
	
	public static void browserLaunch (String browserType, String targetURL){
	
	//ActorLibrary.scenarioExecution(1);
	
		
	System.out.println(osSystem);
	
	switch (osSystem){
		case "Mac OS X":	

			switch (browserType){
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
			switch (browserType){
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
    driver.get(targetURL); //goto the page
	
	}
	
	//public static HashMap<String, String> getAssertionMap() {
        //return AssertionGrid;
   //}
	
}
