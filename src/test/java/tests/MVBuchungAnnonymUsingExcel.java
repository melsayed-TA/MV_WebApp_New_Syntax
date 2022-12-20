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
	private int counter = 1;
	
	//Get test data using the excel reader class --> (ReadExcelFileData) 
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
	@Description("Given that the environment is ready to be tested, When I try to book a ride using the MV-WebApp, Then a booking number will be displayed.")
	@Test(dataProvider = "MVTestData", description = "Prüfen, ob Einbuchung in der MV-WebApp funktioniert und Auslesen der Buchungsnummer")
	public void durchfuehrenMVEinbuchung(String buchungssprache, String zulassungsland, String kennzeichen, String schadstoffklasse_title_soll) {
					
		ReportManager.log("**************MV-Einbuchung: " + counter + " wird durchgeführt**************");
		
		//1. Navigation
		//A_HomePage homeSeite = new A_HomePage(driver);
		E_SchadstoffklassePage sskSeite = new A_HomePage(driver).navigateToMVWebApp(getUrl())
																.waehleBuchungspracheAus(buchungssprache)
																.klickeStarteEinbuchungUndNavigiereZurFahrbeginnSeite()
																.klickeWeiterInDerFahrtbeginnSeite().getZulassungsPage()
																.waehleZulassungslandAus(zulassungsland)
																.klickeWeiterUndNavigiereZurKennzeichenSeite()
																.gebeKennzeichenFeldEin(kennzeichen)
																.klickeWeiterUndNavigiereZurSchadstoffklasseSeite();
																//.navigiereZurHomeSeite();	--> Navigation occurs in the test itself!
		
		// 2. Assertions
		driver.assertThat().element(sskSeite.getEuroVierButton()).exists().withCustomReportMessage("Check if Euro 4 Button Exists").perform();
		driver.assertThat().element(sskSeite.getPageTitle()).text().contains(schadstoffklasse_title_soll).withCustomReportMessage("Check if Schadstoffklasse page title is correct").perform();
		
		ReportManager.log("**************MV-Einbuchung: " + counter + " erfolgreich durchgeführt**************");
		counter = counter + 1;
	}
}
