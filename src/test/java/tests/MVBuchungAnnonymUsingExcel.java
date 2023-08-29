package tests;

import java.io.IOException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.shaft.tools.io.ReportManager;
import io.qameta.allure.Description;
import pages.A_HomePage;
import pages.E_SchadstoffklassePage;
import utilities.ExcelFileDataReader;

public class MVBuchungAnnonymUsingExcel extends TestBase {
	private int counter;
	
	//Get test data using the excel reader class --> (ExcelFileDataReader) --> ITERATIONS!
	//Data driven testing with Excel and TestNG annotation "@DataProvider"
	@DataProvider(name="MVTestData")
	public static String[][] getTestData() throws IOException
	{
		String excelFilePath	= System.getProperty("user.dir") + "\\src\\test\\resources\\testDataFiles\\MV_TestData.xlsx";
		String sheetName		= "MV_Testdaten_Iterations";
		ExcelFileDataReader objExcel = new ExcelFileDataReader(excelFilePath, sheetName);
		String[][] excelData = objExcel.getExcelData();
		return excelData;
	}

	//Test Case
	@Description("Given that the environment is ready to be tested, \nWhen I try to book a ride using the MV-WebApp, \nThen a booking number will be displayed.")
	@Test(dataProvider = "MVTestData", description = "Prüfen, ob Einbuchung in der MV-WebApp funktioniert")
	public void durchfuehrenMVEinbuchung(String buchungssprache, String zulassungsland, String kennzeichen, String schadstoffklasse_title_soll) {
		counter = 1;			
		ReportManager.log("**************MV-Einbuchung Iteration #" + counter + " wird durchgeführt**************");
		
		// 1. Navigation
		E_SchadstoffklassePage sskSeite = new A_HomePage(driver).navigateToMVWebApp(getUrl())
																.waehleBuchungsprache(buchungssprache)
																.klickeStreckeEinbuchen()
																.klickeWeiter().getZulassungsPage()
																.waehleZulassungsland(zulassungsland)
																.klickeWeiter()
																.gebeKennzeichenEin(kennzeichen)
																.klickeWeiter()
																.pruefeVierEuroButtonExistiert();
		
		// 2. Assertions (Note: Assertion can also be a method inside the E_SchadstoffklassePage class and get called directly in the first part ^, with this you would not have this 2nd part)
		//driver.assertThat().element(sskSeite.getEuroVierButton()).exists().withCustomReportMessage("Check that Euro 4 Button Exists").perform();  //Done in part one --> Better Approach
		driver.assertThat().element(sskSeite.getPageTitle()).text().contains(schadstoffklasse_title_soll).withCustomReportMessage("Check that Schadstoffklasse page title is displayed correctly").perform(); //Another Approach (Note that the expected value is read from an excel file!)
		
		ReportManager.log("**************MV-Einbuchung #" + counter + " wurde erfolgreich durchgeführt**************");
		counter = counter + 1;
	}
}
