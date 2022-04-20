package mycore;

public class MyConstants {
	
	public static final String AttendancePortalLink = "http://ftattendance.nrifintech.com/Dashboard/Dashboard";
	public static final String UsernameBoxId = "input#UserID";
	public static final String PasswordBoxId = "input#PWD";
	public static final String LoginButtonId = "div.rightside > input[type=\"submit\"]";
	public static final String AttendanceImgButtonId = "li:nth-child(1) > a > img";
	public static final String ManageDropDownAndRegularizationOptionId = "document.querySelector(\"body > section.wrapper > section > section > nav > ul > li:nth-child(1) > ul > li:nth-child(2) > a\")";
    public static final String SignInIconId = "tr#trsignin span > span";
	public static final String SignOutIconId = "tr#trsignout span > span";
	public static final String CalendarIconId = "tr:nth-child(3) > td:nth-child(2) > div > div > span > span";
	public static final String MonthYearIconId = "document.querySelector(\"body > div.t-animation-container > div > div > a.t-link.t-nav-fast\")";
	public static final String NextMonthIconId = "document.querySelector(\"body > div.t-animation-container > div > div > a.t-link.t-nav-next > span\")";
	public static final String PreviousMonthIconId = "document.querySelector(\"body > div.t-animation-container > div > div > a.t-link.t-nav-prev > span\")";
    public static final String ApplyButtonId = "input[name=\"submitButton\"]";
    public static final String StatusMessageId = "//*[@id=\"divMsg\"]";
    public static final String StatusMessageId2 = "//*[@id=\"divMsg1\"]";
    public static final String StatusCloseIconId = "document.querySelector(\"#btnClose\")";
    public static final String StatusCloseIconId2 = "document.querySelector(\"#btnClose1\")";
    public static final String ManageDropDownAndManageAttendanceOptionId = "document.querySelector(\"body > section.wrapper > section > section > nav > ul > li:nth-child(1) > ul > li:nth-child(3) > a\")";
    public static final String ShowDetailsButtonId = "document.querySelector(\"body > section.wrapper > section > section > div > div.right-column > form:nth-child(2) > fieldset > table > tbody > tr:nth-child(4) > td > div > input[type=submit]\")";
	
    public static final String ExistsAlready = "Attandance record already exists for the specified date !!";
    public static final String AppliedAlready = "Attandance already applied for the specified date !!";
    public static final String HolidayOrWeekend = "Reason is mandatory for holidays & weekends when regularization type is Work_From_Home_Temporary_Basis !!";
    public static final String FutureApplyError = "Regularization can only be applied on past date !!";
    public static final String IncorrectFinancialYear = "You can only apply regularization for current financial year !!";
    public static final String Error = "Error:";
    public static final String Success = "Success:";
}
