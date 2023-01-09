package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileDataReader {

	//Read the test data from the properties file
	
	//public static Properties userData = GetPropertiesFile(System.getProperty("user.dir") + "\\src\\test\\resources\\testDataFiles\\userdata.properties");
	public static Properties userData = GetPropertiesFile("\\src\\test\\resources\\testDataFiles\\userdata.properties");

	
	//Method to read data from properties file
	private static Properties GetPropertiesFile(String path) {
		
		Properties prop = new Properties();

		//Stream for reading the properties file
		try {
			FileInputStream stream = new FileInputStream(path);
			prop.load(stream);
		
		} catch (FileNotFoundException e) {
			//Print error if occurred
			System.out.println("Error occurred (File not Found): " + e.getMessage());
			
		} catch (IOException e) {
			System.out.println("Error occurred (No Input in File): " + e.getMessage());
		}
		catch (NullPointerException e) {
			System.out.println("Error occurred (No Input in File): " + e.getMessage());
		}	
		
		return prop;
	}
	
}
