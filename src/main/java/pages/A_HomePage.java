package pages;

import org.openqa.selenium.By;
import com.shaft.driver.SHAFT;


public class A_HomePage extends PageBase{
	
	//Constructor
	public A_HomePage(SHAFT.GUI.WebDriver driver) {
		super(driver);
	}
	
	//Elements locators (Here id is used as it is unique)
	private By loginButton				= By.id("btn_login");
	private By deutschSpracheButton		= By.id("language_de");
	private By polnischSpracheButton	= By.id("language_pl");
	private By streckeEinbuchenButton	= By.id("btn_start_booking");
	private By willkommenNachticht		= By.id("header-box");
	private By usernameField			= By.id("userName");
	private By passwordField			= By.id("userPassword");
	private By loginButtonLoginMaske	= By.id("btn-forward");
	
	
	//Actions
	public A_HomePage navigateToMVWebApp(String url) {
		driver.browser().navigateToURL(url);
		return this;
	}
	
	
	/*public String ermittleWillkommenNachricht() {
		String willkommenNachtichtIst = driver.element().getText(willkommenNachticht);	
		return willkommenNachtichtIst;
	}*/
	
	
	/*public boolean pruefeExistenzStreckeEinbuchenButton() {
		boolean isStreckeEinbuchenAngezeigt = driver.getDriver().findElement(streckeEinbuchenButton).isDisplayed();		//NATIVE SELENIUM (Bec. isDisplayed() method is missed). Note that driver.getDriver() returns the native selenium driver, which you can use to perform any native selenium action)
		return isStreckeEinbuchenAngezeigt;
	}*/
	
	
	public A_HomePage waehleBuchungspracheAus(String sprache) {
		
		if (sprache.toUpperCase().contains("DEUTSCH") == true) {
			driver.element().click(deutschSpracheButton);
		} else if (sprache.toUpperCase().contains("POLSKI") == true) {
			driver.element().click(polnischSpracheButton);
		} else {	//other languages to be implemented later
			
		}
		
		return this;
	}
	
	
	public A_HomePage loginMitUser(String username, String password) {
		driver.element().click(loginButton);
		driver.element().type(usernameField, username);
		driver.element().type(passwordField, password);
		driver.element().click(loginButtonLoginMaske);
		
		return this;
	}
	
	
	public B_FahrtbeginnPage klickeStarteEinbuchungUndNavigiereZurFahrbeginnSeite() {
		driver.element().click(streckeEinbuchenButton);
		
		/*B_FahrtbeginnPage fahrtbeginnPage = new B_FahrtbeginnPage(driver);*/
		return new B_FahrtbeginnPage(driver);	 //return the next page (Fahrtbeginn) --> Fluent design
	}
}

