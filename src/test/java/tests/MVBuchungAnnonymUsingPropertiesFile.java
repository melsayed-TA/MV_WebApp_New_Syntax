package tests;

import org.testng.annotations.Test;
import pages.A_HomePage;
import utilities.PropertiesFileDataReader;

public class MVBuchungAnnonymUsingPropertiesFile extends TestBase {
	
	//Read the test data from the properties file
	private String zulassungsland	= PropertiesFileDataReader.userData.getProperty("zulassungsland");
	private String kennzeichen 		= PropertiesFileDataReader.userData.getProperty("kennzeichen");
		
	/*@DataProvider(name="MVTestData")
	public static Object[][] testData()
	{
		Object[][] mvTestData = new Object[][] { {"DE", "HH AB 123"}, {"PL", "SG 49125"} };
		return mvTestData;
	}*/

	
	@Test()
	public void durchfuehrenMVEinbuchung() {
		
		String [] arrZulassungsland = zulassungsland.split(",");
		String [] arrkennzeichen 	= kennzeichen.split(",");
		
		int arrLength = arrZulassungsland.length;
		for (int i = 0; i < arrLength; i++) 		//looping through all test data
		{
			System.out.println("Iteration number/Test number --> " + Integer.toString(i+1));
			System.out.println("Zulassungsland to be used --> " + arrZulassungsland[i]);
			System.out.println("Kennzeichen to be used --> " + arrkennzeichen[i]);
		
			//Print, to make sure data is correctly read from the properties file
			System.out.println("Data from data provider, zulassungsland: " + zulassungsland);
			System.out.println("Data from data provider, kennzeichen: " + kennzeichen);
		
			//Test Case
			A_HomePage homeSeite = new A_HomePage(driver);
			homeSeite.waehleBuchungspracheAus("Deutsch")
					 .klickeStarteEinbuchungUndNavigiereZurFahrbeginnSeite()
					 .klickeWeiterInDerFahrtbeginnSeite().getZulassungsPage()
					 .waehleZulassungslandAus(arrZulassungsland[i])
					 .klickeWeiterUndNavigiereZurKennzeichenSeite()
					 .gebeKennzeichenFeldEin(arrkennzeichen[i])
					 .klickeWeiterUndNavigiereZurSchadstoffklasseSeite()
					 .navigiereZurHomeSeite();
		}
		
	}
}
