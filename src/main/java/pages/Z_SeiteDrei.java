package pages;

import org.openqa.selenium.WebDriver;

import com.shaft.driver.SHAFT;

public class Z_SeiteDrei extends PageBase{
	
	//Constructor
	public Z_SeiteDrei(SHAFT.GUI.WebDriver driver) {
		super(driver);
	}
	
	private B_Regnutzer_Fahrzeuge fahrzeugePage = new B_Regnutzer_Fahrzeuge(driver);
	private C_ZulassungslandPage zulassungsPage = new C_ZulassungslandPage(driver);
	
	public B_Regnutzer_Fahrzeuge getFahrzeugePage() {
		return fahrzeugePage;
	}
	
	public C_ZulassungslandPage getZulassungsPage() {
		return zulassungsPage;
	}
	
}
