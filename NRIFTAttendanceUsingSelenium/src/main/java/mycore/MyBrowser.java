package mycore;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.WebDriver;

public class MyBrowser {
	
	public static void main(String[] args) throws InterruptedException, ParseException, IOException 
	{	
		//Made with Love by Kunal Chand
		MyAPIs myAPIs = new MyAPIs();
		WebDriver driver = myAPIs.openAttendancePortal();
		
		myAPIs.logInAndNavigate(driver, "KUNALCHA", "MyPassword");
		
		Set<String> leaves = new HashSet<String>(Arrays.asList("16 December 2021", "17 December 2021", "30 November 2021"));
		myAPIs.setLeaves(leaves);
		
		myAPIs.setDateTimeAndApplyWithTodaysDate(driver, "08:30", "18:30", "C:\\Users\\kunalcha\\Desktop\\Log.txt");
		//myAPIs.setDateTimeAndApplyWithAnyDate(driver, "02", "February", "2022", "08:30", "18:30", "C:\\Users\\kunalcha\\Desktop\\Log.txt");
		
		myAPIs.openManageAttendancePage(driver);
		
	    myAPIs.closeBrowserAfterNSeconds(driver, 45);
	}
}
