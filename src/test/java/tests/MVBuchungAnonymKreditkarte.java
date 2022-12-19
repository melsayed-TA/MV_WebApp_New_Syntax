package tests;

import org.testng.annotations.Test;
import com.shaft.tools.io.ReportManager;
import io.qameta.allure.Description;
import pages.A_HomePage;

public class MVBuchungAnonymKreditkarte extends TestBase{
	String testcaseId 		 	= "Anonym_Kreditkarte";
	//ExcelFileDataReader excel	= new ExcelFileDataReader(getExcelPathTestdaten()); --> Done in Testbase!
	String buchungssprache 		= getExcelTestDaten().getCellDataByTestcaseId(testcaseId, "Buchungssprache");			//Read buchungssprache from excel by key/testcaseId 
	String kennzeichen			= getExcelTestDaten().getCellDataByTestcaseId(testcaseId, "Kennzeichen");				//Read kennzeichen from excel by key/testcaseId 
	String zulassungsland		= getExcelTestDaten().getCellDataByTestcaseId(testcaseId, "Zulassungsland");			//Read zulassungsland from excel by key/testcaseId
	
	@Description("Dieser Test prüft, ob eine MV-Buchung mit KK durchgeführt werden kann. ")
	@Test()
	public void durchfuehrenMVEinbuchung() {	
			
		System.out.println(buchungssprache  + " , " + kennzeichen + " , " + zulassungsland);
		ReportManager.log("Testdaten: " + buchungssprache  + " , " + kennzeichen + " , " + zulassungsland);
		
		//Fluent design!
		//A_HomePage homeSeite = new A_HomePage(driver);
		new A_HomePage(driver).navigateToMVWebApp(getUrl())
							  .waehleBuchungspracheAus(buchungssprache)
							  .klickeStarteEinbuchungUndNavigiereZurFahrbeginnSeite()
							  .klickeWeiterInDerFahrtbeginnSeite().getZulassungsPage()
							  .waehleZulassungslandAus(zulassungsland)
							  .klickeWeiterUndNavigiereZurKennzeichenSeite()
							  .gebeKennzeichenFeldEin(kennzeichen)
							  .klickeWeiterUndNavigiereZurSchadstoffklasseSeite();
	}
}
