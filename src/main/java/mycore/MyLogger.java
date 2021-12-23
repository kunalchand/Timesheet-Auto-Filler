package mycore;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {

	public Logger logger;
	
	public MyLogger(String fileName) throws IOException 
	{
		try {
			File file = new File(fileName);
			if(!file.exists())
			{
				file.createNewFile();
			}
			FileHandler fileHandler = new FileHandler(fileName, true);
			logger = Logger.getLogger("test");
			logger.addHandler(fileHandler);
			fileHandler.setFormatter(new SimpleFormatter());
		}catch(Exception e) {
			//In case of any exception, stop logging the data
		}
	}
}
