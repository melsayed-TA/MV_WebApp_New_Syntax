package pages;

import org.openqa.selenium.By;
import com.shaft.driver.SHAFT;


public class A_HomePage extends PageBase{
	
	//Constructor
	public A_HomePage(SHAFT.GUI.WebDriver driver) {
		super(driver);
	}
	
	
	//Elements locators (Here id is used as it is unique)
	/*WebElement loginButton			= driver.findElement(By.id("btn_login"));		//Native selenium
	WebElement streckeEinbuchenButton	= driver.findElement(By.id("btn_start_booking"));
	WebElement deutschSpracheButton		= driver.findElement(By.id("language_de"));*/
	
	private By loginButton				= By.id("btn_login");
	private By deutschSpracheButton		= By.id("language_de");
	private By polnischSpracheButton	= By.id("language_pl");
	private By streckeEinbuchenButton	= By.id("btn_start_booking");
	private By willkommenNachticht		= By.id("header-box");
	private By usernameField			= By.id("userName");
	private By passwordField			= By.id("userPassword");
	private By loginButtonLoginMaske	= By.id("btn-forward");
	
	
	//Actions (Methods)
	public String ermittleWillkommenNachricht() {
		//String willkommenNachtichtIst = ElementActions.getText(driver, willkommenNachticht);	//Old syntax
		//return willkommenNachtichtIst;
		
		String willkommenNachtichtIst = driver.element().getText(willkommenNachticht);	//New syntax
		return willkommenNachtichtIst;
	}
	
	
	public boolean pruefeExistenzStreckeEinbuchenButton() {
		//boolean isStreckeEinbuchenAngezeigt = ElementActions.isElementDisplayed(driver, streckeEinbuchenButton);
		//return isStreckeEinbuchenAngezeigt;
		
		boolean isStreckeEinbuchenAngezeigt = driver.getDriver().findElement(streckeEinbuchenButton).isDisplayed();		//NATIVE SELENIUM (Bec. is displayed method is missed in the new syntax). Note that driver.getDriver() returns the native selenium driver
		return isStreckeEinbuchenAngezeigt;
	}
	
	
	public A_HomePage waehleBuchungspracheAus(String sprache) {
		//deutschSpracheButton.click();
		
		if (sprache.toUpperCase().contains("DEUTSCH") == true) {
			//ElementActions.click(driver, deutschSpracheButton);
			driver.element().click(deutschSpracheButton);
		} else if (sprache.toUpperCase().contains("POLSKI") == true) {
			//ElementActions.click(driver, polnischSpracheButton);
			driver.element().click(polnischSpracheButton);
		} else {	//other languages to be implemented later
			
		}
		
		return this;
	}
	
	
	public A_HomePage loginMitUser(String username, String password) {
		//loginButton.click();						// Here native Selenium is used (not optimal!)
		//ElementActions.click(driver, loginButton); //Here shaft engine is used! (old syntax)
		driver.element().click(loginButton);

		//ElementActions.type(driver, usernameField, username);
		driver.element().type(usernameField, username);
		
		//ElementActions.type(driver, passwordField, password);
		driver.element().type(passwordField, password);
		
		//ElementActions.click(driver, loginButtonLoginMaske);
		driver.element().click(loginButtonLoginMaske);
		
		return this;
	}
	
	
	public B_FahrtbeginnPage klickeStarteEinbuchungUndNavigiereZurFahrbeginnSeite() {
		//streckeEinbuchenButton.click();
		//ElementActions.click(driver, streckeEinbuchenButton);
		driver.element().click(streckeEinbuchenButton);
		
		B_FahrtbeginnPage fahrtbeginnPage = new B_FahrtbeginnPage(driver);
		return fahrtbeginnPage;	 //return the next page (Fahrtbeginn) --> Fluent design
	}
}

