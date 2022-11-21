package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.shaft.driver.SHAFT;
import com.shaft.gui.element.ElementActions;

public class B_FahrtbeginnPage extends PageBase{
		
	//Constructor
	public B_FahrtbeginnPage(SHAFT.GUI.WebDriver driver) {
		super(driver);
	}

	//Elements Locators
	private By weiterButton = By.id("btn-forward");
	
	
	//Actions
	public /*C_ZulassungslandPage*/ Z_SeiteDrei klickeWeiterInDerFahrtbeginnSeite() {
		//ElementActions.click(driver, weiterButton);
		driver.element().click(weiterButton);
		
		Z_SeiteDrei seiteDrei = new Z_SeiteDrei(driver);
		return seiteDrei;
	
		/*C_ZulassungslandPage zulassungslandPage = new C_ZulassungslandPage(driver);
		return zulassungslandPage;*/  //The third page can be Fahrzeuge OR Zulassungsland - depending on if booking with a user or anonymously
	}
	
}
