package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import EqualexpertsHotel.EqualexpertsHotel.CoreFunctions;

public class bookingDisplay {

	private static WebElement element = null;
	private static String idHash = "";
	
	public static WebElement editBoxFirstname(WebDriver driver){
		 
        element = driver.findElement(By.cssSelector("#firstname"));

        return element;

        }
	
	public static WebElement editBoxSurname(WebDriver driver){
		 
        element = driver.findElement(By.cssSelector("#lastname"));

        return element;

        }
	
	public static WebElement editBoxPrice(WebDriver driver){
		 
        element = driver.findElement(By.cssSelector("#totalprice"));

        return element;

        }
	
	public static WebElement selectDeposit(WebDriver driver){
		 
        element = driver.findElement(By.cssSelector("#depositpaid"));

        return element;

        }
	
	public static WebElement editCheckInDate(WebDriver driver){
		 
        element = driver.findElement(By.cssSelector("#checkin"));

        return element;

        }
	
	public static WebElement editCheckOutDate(WebDriver driver){
		 
        element = driver.findElement(By.cssSelector("#checkout"));

        return element;

        }
	
	public static WebElement buttonSave(WebDriver driver){
		 
        element = driver.findElement(By.cssSelector("#form > div.row > div.col-md-1 > input[type=" + "button" + "]"));
        		
        return element;

        }

	public static WebElement buttonDelete(WebDriver driver){
		    
        element = driver.findElement(By.xpath("//div[@id='"+ getIdHash() + "']/div[7]/input"));
  
        return element;

        }

	public static WebElement bookingDisplayElementFind (WebDriver driver, String IDString){
		
		//
		element = CoreFunctions.getRowUsingXpath(IDString); // includes a fluent wait and defines the idHash field value.
		
	    return element;
		
		
		}
	

	public WebElement bookingDisplayFirstName(WebDriver driver){
		
		element = driver.findElement(By.cssSelector("css=#"+ getIdHash() + " > div.col-md-2"));
		
		return element;
			
	}
	
	public static void assertDeleteNotPresent(WebDriver driver) {
	    try {
	        driver.findElement(By.xpath("//div[@id='"+ getIdHash() + "']/div[7]/input"));
	    } catch (NoSuchElementException ex) { 
	        /* do nothing, delete button is not present, assert is passed */ 
	    	System.out.println("row " + getIdHash() + " has been deleted.");
	    }
	}

	public static String getIdHash() {
		return idHash;
	}

	public static void setIdHash(String idHash) {
		bookingDisplay.idHash = idHash;
	}
	
	
}
