package tests;


import java.io.FileNotFoundException;
import java.io.IOException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import io.qameta.allure.Description;
import pages.A_HomePage;
import utilities.JSONFileDataReader;


public class MVBuchungAnnonymUsingJSON extends TestBase{
	
		//Get data using JSON reader class (ReadJSONData) 
		//Data driven testing with JSON and TestNG annotation "@DataProvider"
		@DataProvider(name="MV_Testdaten")
		public static Object[][] getTestData() throws JsonIOException, JsonSyntaxException, FileNotFoundException
		{
			//String JSONPath = System.getProperty("user.dir") + "\\src\\test\\resources\\testDataFiles\\simpleJSON.json";
			String JSONPath = "./src\\test\\resources\\testDataFiles\\simpleJSON.json";
			
			Object [][] jsonData = JSONFileDataReader.getdata(JSONPath, "MV_Testdaten", 2 , 3);   //1st 2 is the number of rows and 2nd 2 is the number of columns. ToDo: Get no. of rows and columns
			return jsonData;				
		}

		
		//Test Case
		@Description("Given that the environment is ready to be tested, When I try to book a ride using the MV-WebApp, Then a booking number will be displayed.")
		@Test(dataProvider = "MV_Testdaten", description = "Prüfen, ob Einbuchung in der MV-WebApp funktioniert und Auslesen der Buchungsnummer")
		public void durchfuehrenMVEinbuchung(String buchungssprache, String zulassungsland, String kennzeichen) throws IOException {
		
			//A_HomePage homeSeite = new A_HomePage(driver);
			new A_HomePage(driver).navigateToMVWebApp(getUrl())
								  .waehleBuchungspracheAus(buchungssprache)
								  .klickeStarteEinbuchungUndNavigiereZurFahrbeginnSeite()
								  .klickeWeiterInDerFahrtbeginnSeite().getZulassungsPage()
								  .waehleZulassungslandAus(zulassungsland)
								  .klickeWeiterUndNavigiereZurKennzeichenSeite()
								  .gebeKennzeichenFeldEin(kennzeichen)
								  .klickeWeiterUndNavigiereZurSchadstoffklasseSeite()
								  .assertVierEuroButtonExistiert();
		}	
}
