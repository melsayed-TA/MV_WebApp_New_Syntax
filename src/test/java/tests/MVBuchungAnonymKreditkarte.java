package tests;

import org.testng.annotations.Test;
import com.shaft.tools.io.ReportManager;
import io.qameta.allure.Description;
import pages.A_HomePage;
import pages.E_SchadstoffklassePage;

public class MVBuchungAnonymKreditkarte extends TestBase{
	String testcaseId = "Anonym_Kreditkarte";
	//ExcelFileDataReader excel	= new ExcelFileDataReader(getExcelPathTestdaten()); --> Done in Testbase!
	
	@Description("Dieser Test prüft, ob eine MV-Buchung mit KK durchgeführt werden kann. ")
	@Test()
	public void durchfuehrenMVEinbuchung() {	
		//Read test data from excel by testcase-ID (Key!)
		String buchungssprache 		= getExcelTestDaten().getCellDataByTestcaseId(testcaseId, "Buchungssprache");			//Read buchungssprache from excel by key/testcaseId 
		String kennzeichen			= getExcelTestDaten().getCellDataByTestcaseId(testcaseId, "Kennzeichen");				//Read kennzeichen from excel by key/testcaseId 
		String zulassungsland		= getExcelTestDaten().getCellDataByTestcaseId(testcaseId, "Zulassungsland");			//Read zulassungsland from excel by key/testcaseId
		
		System.out.println(buchungssprache  + " , " + kennzeichen + " , " + zulassungsland);
		ReportManager.log("Testdaten: " + buchungssprache  + " , " + kennzeichen + " , " + zulassungsland);
		
		//Fluent design!
		//A_HomePage homeSeite = new A_HomePage(driver);
		E_SchadstoffklassePage sskPage	= new A_HomePage(driver).navigateToMVWebApp(getUrl())
							  .waehleBuchungspracheAus(buchungssprache)
							  .klickeStarteEinbuchungUndNavigiereZurFahrbeginnSeite()
							  .klickeWeiterInDerFahrtbeginnSeite().getZulassungsPage()
							  .waehleZulassungslandAus(zulassungsland)
							  .klickeWeiterUndNavigiereZurKennzeichenSeite()
							  .gebeKennzeichenFeldEin(kennzeichen)
							  .klickeWeiterUndNavigiereZurSchadstoffklasseSeite();
							  //.assertVierEuroButtonExistiert();
		
		driver.assertThat().element(sskPage.getEuroVierButton()).exists().withCustomReportMessage("Check if Euro 4 Button Exists").perform(); //Assertion

	}
}
