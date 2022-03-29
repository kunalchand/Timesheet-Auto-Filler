# NRIFT-Selenium
* Applying attendance in NRIFT Attendance portal is now automated using Selenium. Enjoy!
* Using this tool you can apply attendance to all the pending days with just a single click.
* Since this is a maven project. Hence, you need to clone this project and import it in eclipse.  

### Steps to Clone
**Step 1:** Open git bash in your desired location.    

![gitBash](https://i.ibb.co/RSHfMWp/image.png)         

**Step 2:** Execute command: "git clone https://github.com/kunalchand/NRIFT-Selenium.git"  

![executeCommand](https://i.ibb.co/mBmFSpd/image.png)     

**Step 3:** You should see a green tick on a newly created folder "NRIFT-Selenium"     

![greenTickOnFolder](https://i.ibb.co/Dk98nLB/image.png)     

### Steps to Import an existing maven project in eclipse
**Step 1:** Right click on the Project Explorer and click on "Import"  

![Import](https://i.ibb.co/SfzPMvD/Import.jpg)   

**Step 2:** Type "Maven" and click on "Existing Maven Projects" and fill  

![ExistingProject](https://i.ibb.co/swFX72J/image.png)   

**Step 3:** Click on Browse and look for folder "NRIFT-Selenium"  

![Browse](https://i.ibb.co/8DR8VmH/image.png)     

![Browse](https://i.ibb.co/jfHg4Ty/image.png)     

### Your project in eclipse should look something like this:   
![Project](https://i.ibb.co/nfBQw1r/image.png)    

### Steps to Set up the project
**Step 1:** Open "MyBrowser.java" file. This is where you have to modify few things.    
**Step 2:** Right click on "chromedriver.exe" (Choose the appropriate version for your browser. Currenlty "chromedriver_V98.exe" works perfectly with my browser) and click on "Properties"        

![properties](https://i.ibb.co/42K57yd/image.png)     

**Step 3:** Copy the FULL path           

![FullPath](https://i.ibb.co/nb5fZLC/image.png)

**Step 4:** Paste the copied link in the parameter of openAttendancePortal("*parameter*") line highlighted in the picture below:

![PasteFullPath](https://user-images.githubusercontent.com/25904232/160552695-5b40fccc-8421-4d16-9555-1fd496e3097d.png)                    

**Step 5:** Change your username and password:     

![ChangeUsername&Password](https://i.ibb.co/XbSJRhD/image.png)      

**Step 6:** You can add or modify dates going inside the HashSet. Basically these days will be skipped and attendance won't be applied on the days you mentioned. The skipped days will get logged in the log file if you specify the path correctly (log file path setup is explained in Step 7).     

![leaves](https://user-images.githubusercontent.com/25904232/160553144-84b16ebd-9a13-4054-8ff2-30ac4c63324a.png)                  

##### NOTE: The dates you specify for leaves must be a String and must follow only one format: "dd MMMM yyyy" eg. "03 January 2021". Any spelling mistake WON'T crash the application. It would simply IGNORE the incorrect date that you specified.    

eg. myAPIs.setLeaves(new HashSet<String>(Arrays.asList("16 December 2021", "7 December 2021", "30 Nov 2021", "03 January 2021", "29 February 2022")));     
* 2nd, 3rd & 5th parameter of leave are mentioned in incorret format.    
* "7 December 2021" should be "**0**7 December 2021"   
* "30 Nov 2021" should be "30 **November** 2021"    
* "29 February 2022" date doesn't exists            

And in case you don't have any leaves to specify, you can mention an empty string inside the HashSet. ie. "**myAPIs.setLeaves(new HashSet<String>(Arrays.asList("")));**" In this scenario, NO date will be skipped.         
      
**Step 7:** Specify the Sign In Time, Sign Out Time and the path for Log file:      
* Even if you don't specify the correct path, the application **WON'T** crash. Remember, applying attendance is given more priority than logging the dates on which attendance was successfully applied.             
* "08:30" is an example of Sign In Time. And "18:30" is an example of Sign Out Time.     
* The Sign In Time & Sign Out Time must be specified in string format and must be an exactly same as the options mentioned in the dropdown in portal.     
* "05:3" or "9:05" is NOT acceptable. The application would CRASH in that case. Alternatives are: "05:00" or "05:30" or "09:00" or "09:30" or "14:30" etc.

![TimeAndlogPath](https://i.ibb.co/3pDhRkp/image.png)    

**Step 8:** Run the "MyBrowser.java" and ENJOY the show:     

![runAndEnjoy](https://i.ibb.co/SJCJMmP/image.png)    

