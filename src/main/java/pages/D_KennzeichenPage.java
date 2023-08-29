package pages;

import org.openqa.selenium.By;
import com.shaft.driver.SHAFT;

public class D_KennzeichenPage extends PageBase{

	//Constructor
	public D_KennzeichenPage(SHAFT.GUI.WebDriver driver) {
		super(driver);
	}

	//Elements Locators
	private By kennzeichenFeld = By.id("license_plate");
	private By weiterButton = By.id("btn-forward");
	
	//Actions
	public D_KennzeichenPage gebeKennzeichenEin(String kennzeichen) {
		driver.element().type(kennzeichenFeld, kennzeichen);
		return this;
	}
	
	public E_SchadstoffklassePage klickeWeiter() {
		driver.element().click(weiterButton);
		
		E_SchadstoffklassePage schadstoffklassePage = new E_SchadstoffklassePage(driver);
		return schadstoffklassePage;
	}
	
}
