package tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import com.shaft.driver.SHAFT;
import utilities.Helper;
import utilities.PropertiesFileDataReader;

//Note: All tests (test cases) will inherit from this TestBase class

public class TestBase {

	//protected static WebDriver driver;				//old syntax
	protected static SHAFT.GUI.WebDriver driver;		//new driver of the new shaft syntax
	private String url	= PropertiesFileDataReader.userData.getProperty("url");	//Read the URL from the properties file
	
	//Start driver and navigate to SUT URL --> Navigation can be moved to the test cases
	@BeforeTest
	public void startDriverAndNavigateToURL() {
		//driver = DriverFactory.getDriver();			//old syntax
		//BrowserActions.navigateToURL(driver, url);	
				
		 driver = new SHAFT.GUI.WebDriver();		//new syntax -> instaniating the shaft webdriver
		 driver.browser().navigateToURL(url);		//Navigating to the url
	}
	
	
	//Quit Driver
	@AfterTest(enabled = true) 
	public void stopDriver() {
		driver.quit();
	}
	
	
	//To be executed after 'EACH' method --> take a screenshot when a test case fails and save it in the Screenshots folder
	@AfterMethod
	public void screenshotOnFailure(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("Testcase failed!");
			Helper.captureScreenshot(driver, result.getName());
			System.out.println("Screenshot took and saved in Screenshots folder.");
		}
	}
}
