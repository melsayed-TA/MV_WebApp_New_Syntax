package tests;

import org.testng.annotations.Test;
import com.shaft.tools.io.ReportManager;
import io.qameta.allure.Description;
import pages.A_HomePage;
import pages.E_SchadstoffklassePage;

public class MVBuchungRegnutzerKreditkarte extends TestBase {
	String testcaseId 		 	= "Regnutzer_Kreditkarte";

	//Test Case
	@Description("Dieser Test prüft, ob eine MV-Buchung mit einem reg. Nutzer und einer KK durchgeführt werden kann. Die Testdaten werden aus einer Exceldatei ausgelesen.")
	@Test(description = "Prüfen, ob Einbuchung mit Regnutzer in der MV-WebApp funktioniert und Auslesen der Buchungsnummer")
	public void durchfuehrenMVEinbuchung_Regnutzer_KK() {	
		
		//Read Test Data from excel file
		String rolle 					= getExcelTestDaten().getCellDataByTestcaseId(testcaseId, "Rolle");					//Read Rolle from testdata excel by key/testcaseId
		String buchungssprache			= getExcelTestDaten().getCellDataByTestcaseId(testcaseId, "Buchungssprache");		//Read buchungssprache from testdata excel by key/testcaseId
		String zulassungsland			= getExcelTestDaten().getCellDataByTestcaseId(testcaseId, "Zulassungsland");		//Read zulassungsland from testdata excel by key/testcaseId
		String kennzeichen				= getExcelTestDaten().getCellDataByTestcaseId(testcaseId, "Kennzeichen");			//Read kennzeichen from testdata excel by key/testcaseId 

		//Get user using Umgebung + Rolle (Using sheet name --> sheet name is the controller) // URL --> Get Name (SystemName/URL) using the excel method, using the UmgebungName + Type (ID/Key) (Sheet name is the controller.)
		String username				   	= getExcelUsers().getCellDataByKey("Rolle", rolle, "Username");			//Read username from SystemsAndUsers excel!!
		String password 				= getExcelUsers().getCellDataByKey("Rolle", rolle, "Password");			//Read password from SystemsAndUsers excel!!
		
		//1. Navigation
		//A_HomePage homeSeite			= new A_HomePage(driver);
		E_SchadstoffklassePage sskPage	= new A_HomePage(driver).navigateToMVWebApp(getUrl())
																.waehleBuchungspracheAus(buchungssprache)
																.loginMitUser(username, password)
																.klickeStarteEinbuchungUndNavigiereZurFahrbeginnSeite()
																.klickeWeiterInDerFahrtbeginnSeite().getFahrzeugePage()				//Using the Z_SeiteDrei object which returns 2 pages, I then decide which page to access depending on the scenario i am testing!
																.klickeAnderesFahrzeugVerwendenUndNavigiereZurZulassungslandPage()			 
																.waehleZulassungslandAus(zulassungsland)
																.klickeWeiterUndNavigiereZurKennzeichenSeite()
																.gebeKennzeichenFeldEin(kennzeichen)
																.klickeWeiterUndNavigiereZurSchadstoffklasseSeite();
		
		//2. Assertion -> Check page title
		//Assert.assertEquals(titleNameActual, titleNameExpected, "Title of Schadstoffklasse page is not as expected!");	//TestNG Framework Assertion
		
		//Shaft assertion
		driver.assertThat().element(sskPage.getEuroVierButton()).exists().withCustomReportMessage("Check if Euro 4 Button Exists").perform();
		driver.assertThat().element(sskPage.getPageTitle()).text().contains("Schadstoffklasse").withCustomReportMessage("Check Schadstoffklasse page title = 'Schadstoffklasse'").perform();
		
		//Normal check, extra step -> to check ReportManager in allure report
		String titleName = driver.element().getText(sskPage.getPageTitle());	//Get title name
			
		if (titleName.contains("Schadstoffklasse") == true) {
			ReportManager.log("Check Page Title = 'Schadstoffklasse' is successful");
		} else {
			ReportManager.log("Check Page Title = 'Schadstoffklasse' has failed!");
		}
	}
}

