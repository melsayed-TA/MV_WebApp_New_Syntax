package pages;

import org.openqa.selenium.By;
import com.shaft.driver.SHAFT;

public class C_ZulassungslandPage extends PageBase{
	
	//Constructor
	public C_ZulassungslandPage(SHAFT.GUI.WebDriver driver) {
		super(driver);
	}

	//Elements Locators
	private By deutschlandButton = By.id("icon-tile-DE");
	private By polenButton		 = By.id("icon-tile-PL");
	private By weiterButton 	 = By.id("btn-forward");
	
	//Actions
	public C_ZulassungslandPage waehleZulassungsland(String zulassungsland) {
		
		if (zulassungsland.toUpperCase().contains("DE")) {
			driver.element().click(deutschlandButton);
		}
		
		else if (zulassungsland.toUpperCase().contains("PL")) 
		{
			driver.element().click(polenButton);
		}
		
		return this; //returning the same object! --> Fluent design
	}
	
	public D_KennzeichenPage klickeWeiter() {
		driver.element().click(weiterButton);
		
		D_KennzeichenPage kennzeichenPage = new D_KennzeichenPage(driver);
		return kennzeichenPage;
	}
}


