package pages;

import com.shaft.driver.SHAFT;

//Note: All page classes will inherit from this PageBase class. 
//This class contains the web driver, so it's the "base" for all page classes, in other words after all the pages classes inherit from this class
//the web driver will be also inherited and it will be used to do locate and perform actions on the web elements of these pages.

public class PageBase {

	protected SHAFT.GUI.WebDriver driver;

	//Constructor 
	public PageBase (SHAFT.GUI.WebDriver driver) {
		this.driver = driver;
	}	
}
