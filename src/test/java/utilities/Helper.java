package utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.shaft.driver.SHAFT;

public class Helper {
	
	//Method to take a screenshot when a test case fails
	public static void captureScreenshot(SHAFT.GUI.WebDriver driver, String scrrenshotName) 
	{
		Path destination = Paths.get("./Screenshots", scrrenshotName + ".png");
		
		try {
			Files.createDirectories(destination.getParent());
			FileOutputStream out = new FileOutputStream(destination.toString());
			out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
			out.close();
			
		} catch (IOException e) {
			System.out.println("Error/Exception while taking a screenshot. Message: " + e.getMessage());
		}
	}
}
