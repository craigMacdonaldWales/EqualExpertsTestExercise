package EqualexpertsHotel.EqualexpertsHotel;


import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class dataUtils {

	public static int milliseconds;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	
	}

	public static int getTimeInt (){
		int i = (int) new Date().getTime();
		System.out.println("Integer : " + i);
		milliseconds = i;
	    //it will return unique value based on time
	    System.out.println(milliseconds);
	    
	    return i;
	    
	}

	public static String dateprep(String dateIn){ // futureproofing :)
		
		//CJM 14/03/2017
		// pass in trigger string for date preparation based on position (currently 'today'), supplied operator and incrementor (for day value).
		
		// find operator
		// it's going to be either plus (+) or minus (-)
		String dateOut = "";
		
		// dangling character fix!
		dateIn = dateIn.replaceAll("\\+", "<add>"); 
		dateIn = dateIn.replaceAll("\\-", "<subtract>");
		
		String operator;
		boolean Plusmarker = dateIn.contains("<add>");
		
		if (!Plusmarker){	
			Plusmarker = dateIn.contains("<subtract>");
			if (Plusmarker){
				operator = "<subtract>";
			} else {
				System.out.println("No operator found in supplied string " + dateIn + ", value unchanged");
				return dateIn;
			}
		
		
		
		} else {
			operator = "<add>";
		}
		
		System.out.println("operator detected:" + operator);
		
		String[] dateArray = dateIn.split(operator);
		String position = dateArray[0];
		
		System.out.println("position:" + position);
		String valueToApply = dateArray[1];
		
		System.out.println("position:" + valueToApply);
		//ateInArray = split
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar calendar = Calendar.getInstance();
		System.out.println("Current Date Time : " + sdf.format(calendar.getTime()));
		
		System.out.println("Date NOW : " + sdf.format(calendar.getTime())); // TIME NOW
		
		Integer valueInt = Integer.valueOf(valueToApply);
		
		switch (operator){
		case "<add>":
			calendar.add(Calendar.DATE, valueInt);
			break;
		case "<subtract":
			calendar.add(Calendar.DATE, -valueInt);
			break;
		}
		
		dateOut = sdf.format(calendar.getTime());
		System.out.println("New Date : " + dateOut);
		
		return dateOut;
		
	}
	
}