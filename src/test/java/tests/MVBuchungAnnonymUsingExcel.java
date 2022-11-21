package tests;

import java.io.IOException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.shaft.tools.io.ReportManager;

import io.qameta.allure.Description;
import pages.A_HomePage;
import utilities.ExcelFileDataReader;


public class MVBuchungAnnonymUsingExcel extends TestBase {
	private int counter = 1;
	
	//Get data using the excel reader class --> (ReadExcelFileData) 
	//Data driven testing with Excel and TestNG annotation "@DataProvider"
	@DataProvider(name="MVTestData")
	public static String[][] getTestData() throws IOException
	{
		String excelFilePath	= System.getProperty("user.dir") + "\\src\\test\\resources\\testDataFiles\\MV_TestData.xlsx";
		String sheetName		= "Sheet1";
		
		ExcelFileDataReader objExcel = new ExcelFileDataReader(excelFilePath);
		String[][] excelData = objExcel.getExcelData(excelFilePath, sheetName);
   
		return excelData;
	}
	
		
	//Test Case
	@Description("Given that the environment is ready to be tested, When I try to book a ride using the MV-WebApp, Then a booking number will be displayed.")
	@Test(dataProvider = "MVTestData", description = "Prüfen, ob Einbuchung in der MV-WebApp funktioniert und Auslesen der Buchungsnummer")
	public void durchfuehrenMVEinbuchung(String zulassungsland, String kennzeichen) {
					
		ReportManager.log("**************MV-Einbuchung: " + counter + " wird durchgeführt**************");
				
		A_HomePage homeSeite = new A_HomePage(driver);
		homeSeite.waehleBuchungspracheAus("Deutsch")
		  		 .klickeStarteEinbuchungUndNavigiereZurFahrbeginnSeite()
				 .klickeWeiterInDerFahrtbeginnSeite().getZulassungsPage()
				 .waehleZulassungslandAus(zulassungsland)
				 .klickeWeiterUndNavigiereZurKennzeichenSeite()
				 .gebeKennzeichenFeldEin(kennzeichen)
				 .klickeWeiterUndNavigiereZurSchadstoffklasseSeite()
				 .navigiereZurHomeSeite();
		
		ReportManager.log("MV-Einbuchung: " + counter + " erfolgreich durchgeführt");
		counter = counter + 1;
	}
}
