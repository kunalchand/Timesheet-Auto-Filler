package mycore;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyAPIs {
	
	private static Set<String> leaves = new HashSet<String>();
	
//	public Set<String> getLeaves() {
//		return leaves;
//	}

	public void setLeaves(Set<String> leaves) {
		MyAPIs.leaves = leaves;
	}

	public WebDriver openAttendancePortal(String driverPath)
	{
		System.setProperty("webdriver.chrome.driver", driverPath);
		WebDriver driver = new ChromeDriver();
		driver.get(MyConstants.AttendancePortalLink);
		return driver;
	}
	
	public void logInAndNavigate(WebDriver driver, String username, String password) throws InterruptedException
	{
		findAndWrite(driver, MyConstants.UsernameBoxId, username);
		findAndWrite(driver, MyConstants.PasswordBoxId, password);		
		findAndClick(driver, MyConstants.LoginButtonId);
		findAndClick(driver, MyConstants.AttendanceImgButtonId);
		findAndClickUsingJs(driver, MyConstants.ManageDropDownAndRegularizationOptionId);
	}
	public void setDateTimeAndApplyWithTodaysDate(WebDriver driver, String signInTime, String signOutTime, String logFilePath) throws InterruptedException, ParseException, IOException
	{
		setDateTimeAndApplyWithAnyDate(driver, getTodaysDate(), getTodaysMonth(), getTodaysYear(), signInTime, signOutTime, logFilePath);
	}
	
	public void setDateTimeAndApplyWithAnyDate(WebDriver driver, String presentDay, String presentMonth, String presentYear, String signInTime, String signOutTime, String logFilePath) throws InterruptedException, ParseException, IOException
	{
		MyLogger myLogger = new MyLogger(logFilePath);
		try{
			myLogger.logger.setLevel(Level.INFO);
		}catch(Exception e) {
			//In case of any exception, stop logging the data
		}
		
		String previousDay = presentDay;
		String previousMonth = presentMonth;
		String previousYear = presentYear;
		
		boolean status = true;
		
		do {
			String[] previousDate = getPreviousDate(previousDay, previousMonth, previousYear);
			previousDay = previousDate[0];
			previousMonth = previousDate[1];
			previousYear = previousDate[2];
			
			openCalendarAndSetDate(driver, previousDay, previousMonth, previousYear);
			setSignInAndSignOutTime(driver, signInTime, signOutTime);
			findAndClick(driver, MyConstants.ApplyButtonId);
			
			Thread.sleep(400);
			logSucess(myLogger, getStatusHeader(driver), getStatusMessage(driver), previousDay, previousMonth, previousYear);
			
			status = getStatusMessage(driver).equals(MyConstants.ExistsAlready);
			Thread.sleep(300);
			closeStatusBox(driver);
			Thread.sleep(350);
		}while(!status);
		
		try {
		myLogger.logger.info("Congratulations, All the pending attendance applications are successfully apllied and soon will get approved by your RM/GM");
		}catch (Exception e){
			//In case of any exception, stop logging the data
		}
	}
	
	public void closeBrowserAfterNSeconds(WebDriver driver, int nSeconds) throws InterruptedException
	{
		driver.manage().window().maximize();
		Thread.sleep(nSeconds*1000);
		driver.close();
	}
	
	public void openManageAttendancePage(WebDriver driver)
	{
		findAndClickUsingJs(driver, MyConstants.ManageDropDownAndManageAttendanceOptionId);
		findAndClickUsingJs(driver, MyConstants.ShowDetailsButtonId);
	}
	
	public String getTodaysDate()
	{
		return Integer.toString(LocalDate.now().getDayOfMonth());
	}
	
	public String getTodaysMonth()
	{
		return CaseUtils.toCamelCase(LocalDate.now().getMonth().toString(), true, ' ');
	}
	
	public String getTodaysYear()
	{
		return Integer.toString(LocalDate.now().getYear());
	}
	
	public static void closeStatusBox(WebDriver driver)
	{
		try {
			findAndClickUsingJs(driver, MyConstants.StatusCloseIconId);
		}
		catch(Exception e) {
			findAndClickUsingJs(driver, MyConstants.StatusCloseIconId2);
		}
	}
	
	public static void logSucess(MyLogger myLogger, String statusHeader, String statusMessage, String Day, String Month, String Year) throws ParseException
	{
		try {
		if(statusHeader.equals(MyConstants.Success))
			myLogger.logger.info("(" + statusMessage + ") " + "Successfully Applied Attendance on: " + generateFullDate(Day, Month, Year));
		else
			myLogger.logger.fine("(" + statusMessage + ") " + "Attendance Apply ERROR on: " + generateFullDate(Day, Month, Year));
	
		}catch(Exception e){
			//In case of any exception, stop logging the data
		}
	}
	
	public static String generateFullDate(String Day, String Month, String Year) throws ParseException
	{
		String givenDateInString = Day + " " + Month + " " + Year;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy");
        Date givenDateInDateFormat = dateFormat.parse(givenDateInString);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE, MMMMM dd, yyyy");
        return dayFormat.format(givenDateInDateFormat);
	}
	
	public static String getStatusHeader(WebDriver driver)
	{
		try {
			return driver.findElement(By.xpath(MyConstants.StatusMessageId)).getText().split("\n")[1];
		}
		catch(Exception e){
			return driver.findElement(By.xpath(MyConstants.StatusMessageId2)).getText().split("\n")[1];
		}
	}
	
	public static String getStatusMessage(WebDriver driver)
	{
		try {
			return driver.findElement(By.xpath(MyConstants.StatusMessageId)).getText().split("\n")[2];
		}
		catch(Exception e){
			return driver.findElement(By.xpath(MyConstants.StatusMessageId2)).getText().split("\n")[2];
		}
	}
	
	public static void openCalendarAndSetDate(WebDriver driver, String targetDay, String targetMonth, String targetYear) throws InterruptedException
	{
		findAndClick(driver, MyConstants.CalendarIconId);
		Thread.sleep(250);
		
		navigateToCorrectMonth(driver, targetMonth, targetYear);
		setDateInAMonth(driver, targetDay, targetMonth);
	}
	
	public static void setSignInAndSignOutTime(WebDriver driver, String signInTime, String signOutTime) throws InterruptedException
	{
		Thread.sleep(300);
		setTime(driver, MyConstants.SignInIconId, signInTime);
		Thread.sleep(300);
		setTime(driver, MyConstants.SignOutIconId, signOutTime);
		Thread.sleep(450);
	}
	
	public static void findAndWrite(WebDriver driver, String cssSelector, String text)
	{
		driver.findElement(By.cssSelector(cssSelector)).sendKeys(text);
	}
	
	public static void findAndClick(WebDriver driver, String cssSelector)
	{
		driver.findElement(By.cssSelector(cssSelector)).click();
	} 
	
	public static void findAndClickUsingJs(WebDriver driver, String element)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(element+".click()");
	} 
	
	public static void setTime(WebDriver driver, String signInOrOut, String time) throws InterruptedException
	{
		findAndClick(driver, signInOrOut); // click on icon for pop-up to appear
		Thread.sleep(300);
		WebElement dropdown = driver.findElement(By.className("t-reset"));
		List<WebElement> options = dropdown.findElements(By.tagName("li"));
		for (WebElement option : options)
		{
		    if (option.getText().equals(time))
		    {
		        option.click(); // click the desired option
		        break;
		    }
		}
	}
	
	@SuppressWarnings("unused")
	public static void setDateInAMonth(WebDriver driver, String targetDay, String targetMonth)
	{
		WebElement table = driver.findElement(By.className("t-content"));
		List<WebElement> options = table.findElements(By.tagName("a"));
		
		for (WebElement option : options)
		{
			String[] title = option.getAttribute("title").split(" ");

			String weekDay = StringUtils.strip(title[0], ",");
			String month = title[1];
			String day = StringUtils.strip(title[2], ",");
			String year = title[3];
			
			//System.out.println(weekDay + ", " + month + " " + day + ", " + year);
			
			if (month.equals(targetMonth) && day.equals(targetDay))
		    {
		        option.click();
		        break;
		    }
		}
	}
	
	public static String findAndReturnTextUsingJs(WebDriver driver, String element)
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript("return " + element + ".text").toString();
	} 
	
	public static void clickNextMonthIcon(WebDriver driver) throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		Thread.sleep(500);
		js.executeScript(MyConstants.NextMonthIconId + ".click()");
		Thread.sleep(500);
	}
	
	public static void clickPreviousMonthIcon(WebDriver driver) throws InterruptedException
	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		
		Thread.sleep(500);
		js.executeScript(MyConstants.PreviousMonthIconId + ".click()");
		Thread.sleep(500);
	}
	
	public static void navigateToCorrectMonth(WebDriver driver, String targetMonth, String targetYear) throws InterruptedException
	{
		String currentMonth = findAndReturnTextUsingJs(driver,MyConstants.MonthYearIconId).split(" ")[0];
		String currentYear = findAndReturnTextUsingJs(driver,MyConstants.MonthYearIconId).split(" ")[1];
		
		int gap = getGap(currentMonth, currentYear, targetMonth, targetYear);
		
		while(gap != 0)
		{
			if(gap<0)
			{
				clickPreviousMonthIcon(driver);
				gap++;
			}
			else if(gap > 0)
			{
				clickNextMonthIcon(driver);
				gap--;
			}
		}
	}
	
	@SuppressWarnings("serial")
	public static int getGap(String currentMonth, String currentYear, String targetMonth, String targetYear)
	{
		final HashMap<String, Integer> monthChart = new HashMap<String, Integer>() {{
		    put("January",1);
		    put("February",2);
		    put("March",3);
		    put("April",4);
		    put("May",5);
		    put("June",6);
		    put("July",7);
		    put("August",8);
		    put("September",9);
		    put("October",10);
		    put("November",11);
		    put("December",12);
		}};
		
		int yearGap = Integer.parseInt(targetYear) - Integer.parseInt(currentYear);
		
		if(yearGap == 0)
		{
			return monthChart.get(targetMonth) - monthChart.get(currentMonth);
		}
		else if(yearGap > 0)
		{
			return monthChart.get(targetMonth) - monthChart.get(currentMonth) + yearGap*12;
		}
		else// if(yearGap < 0)
		{
			return (-1)*(monthChart.get(currentMonth) - monthChart.get(targetMonth) + Math.abs(yearGap)*12);
		}
	}
	
	public static String[] getPreviousDate(String givenDay, String givenMonth, String givenYear) throws ParseException
	{
		final long ONE_DAY_MILLI_SECONDS = 24 * 60 * 60 * 1000;
		final String DATE_FORMAT = "dd MMMMM yyyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		
		String givenDateInString = givenDay + " " + givenMonth + " " + givenYear;
		Date givenDateInDateFormat = dateFormat.parse(givenDateInString);
		
		long previousDateInMilliSeconds = givenDateInDateFormat.getTime() - ONE_DAY_MILLI_SECONDS;
		Date previousDateInDateFormat = new Date(previousDateInMilliSeconds);
		
		String[] previousDateInString = dateFormat.format(previousDateInDateFormat).split(" ");
		String previousDay = previousDateInString[0];
		String previousMonth = previousDateInString[1];
		String previousYear = previousDateInString[2];
		previousDateInString = checkWeekend(previousDay, previousMonth, previousYear) ? getPreviousDate(previousDay, previousMonth, previousYear) : previousDateInString;
		previousDateInString = checkLeave(previousDay, previousMonth, previousYear) ? getPreviousDate(previousDay, previousMonth, previousYear) : previousDateInString;
		
		return previousDateInString;
	}
	
	public static boolean checkLeave(String givenDay, String givenMonth, String givenYear)
	{
//		MyAPIs myAPIs = new MyAPIs();
//		Set<String> leaves = myAPIs.getLeaves();
		
		if (MyAPIs.leaves.contains(givenDay + " " + givenMonth + " " + givenYear)) return true;
		else return false;
	}
	
	public static boolean checkWeekend(String givenDay, String givenMonth, String givenYear) throws ParseException
	{
		String givenDateInString = givenDay + " " + givenMonth + " " + givenYear;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMMM yyyy");
        Date givenDateInDateFormat = dateFormat.parse(givenDateInString);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        if(dayFormat.format(givenDateInDateFormat).matches("Sunday|Saturday")) return true;
        else return false;
	}
}
