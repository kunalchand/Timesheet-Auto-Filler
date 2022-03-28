package mycore;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashSet;
import org.openqa.selenium.WebDriver;

public class MyBrowser {
	
	public static void main(String[] args) throws InterruptedException, ParseException, IOException 
	{	
		//Made with Love by Kunal Chand
		MyAPIs myAPIs = new MyAPIs();
		
		WebDriver driver = myAPIs.openAttendancePortal("C:\\Users\\kunalcha\\Desktop\\WORK\\NRIFT-Selenium\\NRIFTAttendanceUsingSelenium\\drivers\\chromedriver\\chromedriver.exe");
		
		myAPIs.logInAndNavigate(driver, "KUNALCHA", "MyPassword");
		
		myAPIs.setLeaves(new HashSet<String>(Arrays.asList("06 May 2020", "17 November 2020", "30 December 2021")));
		
		myAPIs.setDateTimeAndApplyWithTodaysDate(driver, "08:30", "18:30", "C:\\Users\\kunalcha\\Desktop\\Log.txt");
		//myAPIs.setDateTimeAndApplyWithAnyDate(driver, "02", "February", "2022", "08:30", "18:30", "C:\\Users\\kunalcha\\Desktop\\Log.txt");
		
		myAPIs.openManageAttendancePage(driver);
		
	    myAPIs.closeBrowserAfterNSeconds(driver, 45);
	}
}
