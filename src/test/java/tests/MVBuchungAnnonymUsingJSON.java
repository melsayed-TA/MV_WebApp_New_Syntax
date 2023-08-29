package tests;

import java.io.FileNotFoundException;
import java.io.IOException;
import com.fasterxml.jackson.databind.JsonNode;
import com.shaft.tools.io.ReportManager;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import io.qameta.allure.Description;
import pages.A_HomePage;
import utilities.JSONFileDataReader;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static utilities.JSONFileDataReader.getNumColumns;
import static utilities.JSONFileDataReader.loadJsonFromFile;

public class MVBuchungAnnonymUsingJSON extends TestBase{
	
		//Get data using JSON reader class (ReadJSONData) 
		//Data driven testing with JSON and TestNG annotation "@DataProvider"
		@DataProvider(name="MV_Testdaten")
		public static Object[][] getTestData() throws JsonIOException, JsonSyntaxException, FileNotFoundException
		{
			String JSONFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\testDataFiles\\simpleJSON.json";
			int numRows = 0;
			int numCols = 0;

			//Get size of JSON
			try {
				String jsonData = new String(Files.readAllBytes(Paths.get(JSONFilePath)));
				JSONObject jsonObject = new JSONObject(jsonData);
				JSONArray testDataArray = jsonObject.getJSONArray("MV_Testdaten");
				numRows = testDataArray.length(); // Number of rows
				numCols = 0; // Initialize column count

				if (numRows > 0) {
					JSONObject firstRow = testDataArray.getJSONObject(0);
					numCols = firstRow.length(); // Number of columns in the first row
				}
				ReportManager.log("Number of Json Iterations to be executed: " + numRows);
				ReportManager.log("Number of Json Parameters: " + numCols);

			} catch (IOException | JSONException e) {
				e.printStackTrace();
			}

			Object [][] jsonDataIterations = JSONFileDataReader.getdata(JSONFilePath, "MV_Testdaten", numRows , numCols);
			return jsonDataIterations;
		}
		
		//Test Case
		@Description("Given that the environment is ready to be tested, \nWhen I try to book a ride using the MV-WebApp, \nThen a booking number will be displayed.")
		@Test(dataProvider = "MV_Testdaten", description = "Prüfen, ob Einbuchung in der MV-WebApp funktioniert und Auslesen der Buchungsnummer")
		public void durchfuehrenMVEinbuchung(String buchungssprache, String zulassungsland, String kennzeichen) throws IOException {
		
			//A_HomePage homeSeite = new A_HomePage(driver);
			new A_HomePage(driver).navigateToMVWebApp(getUrl())
								  .waehleBuchungsprache(buchungssprache)
								  .klickeStreckeEinbuchen()
								  .klickeWeiter().getZulassungsPage()
								  .waehleZulassungsland(zulassungsland)
								  .klickeWeiter()
								  .gebeKennzeichenEin(kennzeichen)
								  .klickeWeiter()
								  .pruefeVierEuroButtonExistiert();
		}	
}