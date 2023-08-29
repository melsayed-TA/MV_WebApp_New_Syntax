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
	private By pageTitle		= By.xpath("(//mv-pagetitle[@id='page_title'])[2]");
	private By euroVierButton	= By.id("icon-tile-S4");
	
	//Getters!
	public By getPageTitle() {
		return pageTitle;
	}
	
	public By getEuroVierButton() {
		return euroVierButton;
	}

	//Actions
	public void navigiereZurHomeSeite() {
		driver.element().click(homeButton);
		driver.element().click(jaButton);
	}
	
	public E_SchadstoffklassePage pruefeVierEuroButtonExistiert() {
		driver.assertThat().element(euroVierButton).exists().withCustomReportMessage("Check that the 'Euro 4' Button exists in the Schadstoffklasse page.").perform();	
		return this;
	}
}