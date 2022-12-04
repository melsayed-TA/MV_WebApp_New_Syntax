package tests;

import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.shaft.driver.SHAFT;


public class NavigationTest {
	protected static SHAFT.GUI.WebDriver driver;	
	private String url			= "https://demowebshop.tricentis.com/";
	private By loginButton		= By.className("ico-login");
	private By emailField		= By.id("Email");
	private By passField		= By.id("Password");
	private By searchStoreField = By.id("small-searchterms");
	private By accountEmail		= By.className("account");
	private By userLoginBtn		= By.className("button-1 login-button");
	
	
	@DataProvider(name="WebshopTestdata")
	public static Object[][] testData(){
		Object[][] testData = new Object[][] { {"mustafamagdi@teml.net", "Selenium123"}};
		return testData; 
	}
	
	
	@Test(dataProvider = "WebshopTestdata")
	public void loginToWebshop(String email, String pass) {			
		driver.browser().navigateToURL(url);
		driver.assertThat().element(searchStoreField).isVisible().withCustomReportMessage("Checking that after successful navigation to the url, the search store edit field is displayed").perform();
		driver.element().click(loginButton);
		driver.element().type(emailField, email);
		driver.element().type(passField, pass);
		driver.element().click(userLoginBtn);
		driver.assertThat().element(accountEmail).text().contains(email).withCustomReportMessage("Checking that the account email appears after the successful login to the webshop").perform();
	}
	
	
	@BeforeTest
	public void startDriver() {
		 driver = new SHAFT.GUI.WebDriver();				
	}
	
	
	@AfterTest(enabled = true) 
	public void stopDriver() {
		driver.quit();
	}
}
