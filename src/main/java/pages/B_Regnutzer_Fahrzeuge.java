package pages;

import org.openqa.selenium.By;
import com.shaft.driver.SHAFT;


public class B_Regnutzer_Fahrzeuge extends PageBase {

	//Constructor
	public B_Regnutzer_Fahrzeuge(SHAFT.GUI.WebDriver driver) {
		super(driver);
	}
	
	//Elements Locators
	private By anderesFahrzeugVerwendenButton	= By.id("other_vehicle_item");
	private By weiterButton						= By.id("btn-forward");
	
	
	//Actions (methods)
	public C_ZulassungslandPage klickeAnderesFahrzeugVerwendenUndNavigiereZurZulassungslandPage() {
		//String currentClassName = ElementActions.getAttribute(driver, anderesFahrzeugVerwendenButton, "class");
		String currentClassName = driver.element().getAttribute(anderesFahrzeugVerwendenButton, "class");
		
		if (currentClassName.contains("selection-button-component selection-button-component__other active")) {
			//do nothing, since it is already clicked (activated)
		} 
		else //click if not clicked/activated (click only if not clicked)
		{
			//ElementActions.click(driver, anderesFahrzeugVerwendenButton);
			driver.element().click(anderesFahrzeugVerwendenButton);
		}
		

		//ElementActions.click(driver, weiterButton);	//Click on weiter
		driver.element().click(weiterButton);
		
		C_ZulassungslandPage zulassungslandPage = new C_ZulassungslandPage(driver);
		return zulassungslandPage;
	}
}