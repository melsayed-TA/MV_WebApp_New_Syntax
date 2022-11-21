package tests;

import org.testng.annotations.Test;

import com.shaft.gui.element.ElementActions;
import com.shaft.tools.io.ReportManager;

import io.qameta.allure.Description;
import pages.A_HomePage;
import pages.E_SchadstoffklassePage;
import utilities.ExcelFileDataReader;
import utilities.PropertiesFileDataReader;

public class MVBuchungRegnutzerKreditkarte extends TestBase {
	private String umgebung 				= PropertiesFileDataReader.userData.getProperty("umgebung");	//Read Umgebung from properties file (Best way to store it!)
	
	private String excelPath_testData		= System.getProperty("user.dir") + "\\src\\test\\resources\\testDataFiles\\MV_TestData_Different_Tests.xlsx";
	private String sheetName_testData		= "Sheet1"; 
	
	private String excelPath_User			= System.getProperty("user.dir") + "\\src\\test\\resources\\testDataFiles\\SystemsAndUsers.xlsx";
	private String sheetName_User			= "MV_Users_" + umgebung; 

	
	@Description("Dieser Test prüft, ob eine MV-Buchung mit einem reg. Nutzer und einer KK durchgeführt werden kann. Die Testdaten werden aus einer Exceldatei ausgelesen.")
	@Test(description = "Prüfen, ob Einbuchung mit Regnutzer in der MV-WebApp funktioniert und Auslesen der Buchungsnummer")
	public void durchfuehrenMVEinbuchung_Regnutzer_KK() {	
		//Read Test Data from excel files
		ExcelFileDataReader excelTD = new ExcelFileDataReader(excelPath_testData);
		String kennzeichen			= excelTD.getCellDataByTestcaseId(excelPath_testData, sheetName_testData, "Regnutzer_Kreditkarte", "Kennzeichen");			//Read kennzeichen from testdata excel by key/testcaseId 
		String zulassungsland		= excelTD.getCellDataByTestcaseId(excelPath_testData, sheetName_testData, "Regnutzer_Kreditkarte", "Zulassungsland");		//Read zulassungsland from testdata excel by key/testcaseId
		String rolle 				= excelTD.getCellDataByTestcaseId(excelPath_testData, sheetName_testData, "Regnutzer_Kreditkarte", "Rolle");				//Read Rolle from testdata excel by key/testcaseId

		//Get user using UmgebungName + Rolle (Using sheet name --> sheet name is the controller) // URL --> Get Name (SystemName/URL) using the excel method, using the UmgebungName + Type (ID/Key) (Sheet name is the controller.)
		ExcelFileDataReader excelUsers	= new ExcelFileDataReader(excelPath_User);
		String username				   	= excelUsers.getCellDataByKey(excelPath_User, sheetName_User, "Rolle", rolle, "Username");							//Read username from SystemsAndUsers excel!!
		String password 				= excelUsers.getCellDataByKey(excelPath_User, sheetName_User, "Rolle", rolle, "Password");							//Read password from SystemsAndUsers excel!!
			
		//Test Case
		//1. Navigation
		A_HomePage homeSeite			= new A_HomePage(driver);
		E_SchadstoffklassePage sskPage	= homeSeite.waehleBuchungspracheAus("Deutsch")
										.loginMitUser(username, password)
										.klickeStarteEinbuchungUndNavigiereZurFahrbeginnSeite()
										.klickeWeiterInDerFahrtbeginnSeite().getFahrzeugePage()				//Using the Z_SeiteDrei object which returns 2 pages, I then decide which page to access depending on the scenario i am testing!
										.klickeAnderesFahrzeugVerwendenUndNavigiereZurZulassungslandPage()			 
										.waehleZulassungslandAus(zulassungsland)
										.klickeWeiterUndNavigiereZurKennzeichenSeite()
										.gebeKennzeichenFeldEin(kennzeichen)
										.klickeWeiterUndNavigiereZurSchadstoffklasseSeite();
		
		
		//2. Assertion
		//String titleName = ElementActions.getText(driver, sskPage.getPageTitle());	//old syntax
		String titleName = driver.element().getText(sskPage.getPageTitle());			//new syntax
		
		//Assert.assertEquals(titleNameActual, titleNameExpected, "Title of Schadstoffklasse page is not as expected!");	//TestNG Assertion
		
		//Shaft assertion
		driver.assertThat().element(sskPage.getPageTitle()).text().contains("Schadstoffklasse").withCustomReportMessage("Check Schadstoffklasse page title = 'Schadstoffklasse'").perform();

			
		if (titleName.contains("Schadstoffklasse") == true) {
			ReportManager.log("Check Page Title = 'Schadstoffklasse' is successful");
		} else {
			ReportManager.log("Check Page Title = 'Schadstoffklasse' has failed!");
		}
	}
}

