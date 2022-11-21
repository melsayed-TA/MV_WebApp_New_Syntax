package tests;

import org.testng.annotations.Test;

import io.qameta.allure.Description;
import pages.A_HomePage;
import utilities.ExcelFileDataReader;

public class MVBuchungAnonymKreditkarte extends TestBase{
	private String excelPath = System.getProperty("user.dir") + "\\src\\test\\resources\\testDataFiles\\MV_TestData_Different_Tests.xlsx";
	private String sheetName = "Sheet1"; //can also be read from properties file (optional)
	
	@Description("Dieser Test prüft, ob eine MV-Buchung mit KK durchgeführt werden kann. ")
	@Test()
	public void durchfuehrenMVEinbuchung() {	
		ExcelFileDataReader excel = new ExcelFileDataReader(excelPath);
		String kennzeichen = excel.getCellDataByTestcaseId(excelPath, sheetName, "Anonym_Kreditkarte", "Kennzeichen");			//Read value from excel by key/testcaseId 
		String zulassungsland = excel.getCellDataByTestcaseId(excelPath, sheetName, "Anonym_Kreditkarte", "Zulassungsland");	//Read value from excel by key/testcaseId
		
		System.out.println(kennzeichen + " , " + zulassungsland);
		
		//Fluent design!
		A_HomePage homeSeite = new A_HomePage(driver);
		homeSeite.waehleBuchungspracheAus("Deutsch")
		 		 .klickeStarteEinbuchungUndNavigiereZurFahrbeginnSeite()
				 .klickeWeiterInDerFahrtbeginnSeite().getZulassungsPage()
				 .waehleZulassungslandAus(zulassungsland)
				 .klickeWeiterUndNavigiereZurKennzeichenSeite()
				 .gebeKennzeichenFeldEin(kennzeichen)
				 .klickeWeiterUndNavigiereZurSchadstoffklasseSeite()
				 .navigiereZurHomeSeite();
	}
}
