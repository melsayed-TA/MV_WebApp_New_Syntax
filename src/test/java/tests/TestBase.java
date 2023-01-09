package tests;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.shaft.driver.SHAFT;

import utilities.ExcelFileDataReader;
import utilities.Helper;
import utilities.PropertiesFileDataReader;

//Note: All tests (test cases) will inherit from this TestBase class

public class TestBase {
	//private String url	= PropertiesFileDataReader.userData.getProperty("url");	//Read the URL from the properties file

	protected static SHAFT.GUI.WebDriver driver;
	
	private String umgebung 					= PropertiesFileDataReader.userData.getProperty("umgebung");	//Read Umgebung from properties file (Best way to store it!). Ex: EK1
	
	//private String excelPath_SystemsAndUsers	= System.getProperty("user.dir") + "\\src\\test\\resources\\testDataFiles\\SystemsAndUsers.xlsx";
	private String excelPath_SystemsAndUsers	= "src\\test\\resources\\testDataFiles\\SystemsAndUsers.xlsx";
	
	private String sheetName_Systems			= "Systems_" + umgebung;	//Systems_EK1
	private ExcelFileDataReader excelSystems;
		
	private String url;					   			
	
	private String sheetName_Users				= "MV_Users_" + umgebung; 
	private ExcelFileDataReader excelUsers;				
	
	//private String excelPath_Testdaten 			= System.getProperty("user.dir") + "\\src\\test\\resources\\testDataFiles\\MV_TestData_Different_Tests.xlsx";
	private String excelPath_Testdaten 			= "src\\test\\resources\\testDataFiles\\MV_TestData_Different_Tests.xlsx";
	
	private String sheetName_Testdaten			= "MV_Testdaten"; 														
	private ExcelFileDataReader excelTestDaten;			
	
	//Return App's URL --> To be used in all tests!
	public String getUrl(){
		return url;
	}
	
	public ExcelFileDataReader getExcelTestDaten() {
		return excelTestDaten;
	}

	public ExcelFileDataReader getExcelUsers() {
		return excelUsers;
	}
	
	//@BeforeTest //--> when running more than one test class (from testng.xml), only the first test runs
	//@BeforeSuite //--> when running more than one test class (from testng.xml), only the first test runs
	@BeforeMethod
	//@BeforeClass
	public void startDriverAndNavigateToURL() {	
		 driver			= new SHAFT.GUI.WebDriver();												//Instantiating shaft's web driver -> Can be used in all Tests (since all tests inherits from this test base class)
		 excelSystems	= new ExcelFileDataReader(excelPath_SystemsAndUsers, sheetName_Systems);	//Excel object for reading Systems/URLs (instantiation) -> Can be used in all Tests (since all tests inherits from this test base class)
		 excelUsers		= new ExcelFileDataReader(excelPath_SystemsAndUsers, sheetName_Users);		//Excel object for reading Users (instantiation) -> Can be used in all Tests (since all tests inherits from this test base class)
		 excelTestDaten = new ExcelFileDataReader(excelPath_Testdaten, sheetName_Testdaten);		//Excel object for reading test data (instantiation) -> Can be used in all Tests (since all tests inherits from this test base class)
		 url			= excelSystems.getCellDataByKey("Systemart", "MV" , "URL");					//Get URL from excel -> Can be used in all Tests (since all tests inherits from this test base class) 
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
