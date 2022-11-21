package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.A_HomePage;

public class MVBuchungAnnonym extends TestBase{
	
	//private String kennzeichen = "HH AB 123";		//Static value! 
	
	//Passing test data using the TestNG annotation "@DataProvider"
	@DataProvider(name="MVTestData")
	public static Object[][] testData()
	{
		Object[][] testData = new Object[][] { {"DE", "HH AB 123"}, {"PL", "SG 49125"} };
		return testData; //Returning a 2D array of objects
	}
	
	
	@Test(dataProvider = "MVTestData")
	public void durchfuehrenMVEinbuchung(String zulassungsland, String kennzeichen) {	
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
