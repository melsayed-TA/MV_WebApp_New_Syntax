package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.A_HomePage;

public class MVBuchungAnnonym extends TestBase{
	
	//private String kennzeichen = "HH AB 123";		//Static value! 
	
	//Passing test data using the TestNG annotation "@DataProvider"
	@DataProvider(name="MVTestData")
	public static Object[][] testData()
	{
		Object[][] testData = new Object[][] { {"Deutsch", "DE", "HH AB 123"}, {"Polski", "PL", "SG 49125"} };
		return testData; //Returning a 2D array of objects
	}
	
	
	@Test(dataProvider = "MVTestData")
	public void durchfuehrenMVEinbuchung(String buchungssprache, String zulassungsland, String kennzeichen) {	
		//Fluent design!
		//A_HomePage homeSeite = new A_HomePage(driver);
		new A_HomePage(driver).navigateToMVWebApp(getUrl())
							  .waehleBuchungspracheAus(buchungssprache)
							  .klickeStarteEinbuchungUndNavigiereZurFahrbeginnSeite()
							  .klickeWeiterInDerFahrtbeginnSeite().getZulassungsPage()
							  .waehleZulassungslandAus(zulassungsland)
							  .klickeWeiterUndNavigiereZurKennzeichenSeite()
							  .gebeKennzeichenFeldEin(kennzeichen)
							  .klickeWeiterUndNavigiereZurSchadstoffklasseSeite()
							  .assertVierEuroButtonExistiert();
	} 
}


/*mvn clean test -Ptestng -Dtestng.suiteXmlFile=RegressionSuite.xml
1. mvn: This is the command to invoke the Maven build tool. You use it to interact with your Maven projects and trigger various build lifecycle phases.

2. clean: This is a Maven goal that is part of the default lifecycle. It's used to clean up the output directories and remove any artifacts generated from previous builds. In this context, it ensures a fresh build environment before running the tests.

3. test: This is another Maven goal from the default lifecycle. It compiles and runs the tests in the project. When you execute this goal, Maven will look for test source code (usually in the src/test directory), compile it, and then execute the tests.

4. -Ptestng: This uses the -P flag to specify an active profile for the build. In this case, the profile named testng will be activated. Profiles in Maven allow you to define different build configurations for different scenarios. Here, the testng profile might include specific configuration settings for TestNG-based testing.

5. -Dtestng.suiteXmlFile=my-test-suite.xml: This uses the -D flag to set a system property for the build. It's setting the testng.suiteXmlFile property to my-test-suite.xml. This property is used by TestNG to specify which test suite XML file should be executed. TestNG test suites are XML files that define which test classes or methods should be run as part of a test execution.

So, when you run the command mvn clean test -Ptestng -Dtestng.suiteXmlFile=my-test-suite.xml:

The clean goal will ensure a clean build environment.
The test goal will compile and run tests in the project.
The testng profile will be activated, potentially applying specific configuration.
The testng.suiteXmlFile property will be set to my-test-suite.xml, telling TestNG to use this specific XML file to determine which tests to run.
Make sure to replace my-test-suite.xml with the actual path or name of your TestNG suite XML file. This command assumes that you have Maven and TestNG set up properly in your project.
 */