package pages;

import org.openqa.selenium.By;
import com.shaft.driver.SHAFT;

public class E_SchadstoffklassePage extends PageBase{

	//Constructor
	public E_SchadstoffklassePage(SHAFT.GUI.WebDriver driver) {
		super(driver);
	}

	
	//Elements Locators
	private By homeButton		= By.xpath("//div[@aria-label='Startseite öffnen']");
	private By jaButton			= By.xpath("//mv-button[@id='btn-confirm']");
	//private By zurueckButton	= By.xpath("//mv-button[@id='btn-backward']");
	private By pageTitle		= By.xpath("(//mv-pagetitle[@id='page_title'])[2]");
	private By euroVierButton	= By.id("icon-tile-S4");
	
	//Getter!
	public By getPageTitle() {
		return pageTitle;
	}
	
	public By getEuroVierButton() {
		return euroVierButton;
	}

	//Actions
	public void navigiereZurHomeSeite() {
		//ElementActions.click(driver, homeButton);
		driver.element().click(homeButton);
		//ElementActions.click(driver, jaButton);
		driver.element().click(jaButton);
	}
	
	/*public void goHome() {
		//while (ElementActions.isElementDisplayed(driver, zurueckButton) == true	&& ElementActions.isElementClickable(driver, zurueckButton) == true) { // why throws error when boolean = false?
		while (driver.findElements(zurueckButton).size() > 0 && driver.findElement(zurueckButton).isDisplayed() == true && driver.findElement(zurueckButton).isEnabled() == true) {		
			ElementActions.click(driver, zurueckButton);
			
			if (ElementActions.getText(driver, zurueckButton).contains("Home")) {
				ElementActions.click(driver, zurueckButton);
				ElementActions.click(driver, jaButton);
			}
			
		}
	}*/
	
	
	

}

