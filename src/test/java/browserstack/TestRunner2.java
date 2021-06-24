package browserstack;

import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import browserstack.driverconfig.Platform;
import browserstack.driverconfig.WebDriverFactory;
import browserstack.stepdefs.BaseTest;
import browserstack.utils.AllureReportConfigurationSetup;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;



/* THIS FILE IS CREATED TEMPORARILY */
@CucumberOptions(features = "src/test/resources/com/browserstack", glue = "browserstack.stepdefs")
public class TestRunner2   {
	
	private final WebDriverFactory webDriverFactory = new WebDriverFactory();
	private final Platform platform = new Platform();
	private WebDriver webDriver;
	
	

	private TestNGCucumberRunner testNGCucumberRunner;
	
	@BeforeSuite
	public void setupReport()
	{
		AllureReportConfigurationSetup.prepareAllureResultsFolder();
	}

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		
	}
	
	
	
	//public TestRunner(String testMethodName, Platform platform, WebDriverFactory webDriverFactory) {
		//this.testMethodName = testMethodName;
		//this.platform = platform;
		//this.webDriverFactory = webDriverFactory;
//	}

	@BeforeMethod
	public WebDriver createDriver() throws MalformedURLException {


		String[] specificCapabilities = new String[0];

		if (this.webDriver == null) {
			this.webDriver = this.webDriverFactory.createWebDriverForPlatform(platform, "",
					specificCapabilities);
		}
		return this.webDriver;
	}

	

	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
	public void feature(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
		testNGCucumberRunner.runScenario(pickleWrapper.getPickle());

	}

	@DataProvider(name = "hardCodedBrowsers",parallel = true)
	public Object[][] scenarios(Method testMethod) {	
		 return new Object[][]{
             new Object[]{"Chrome", "latest", "Windows"},
             new Object[]{"firefox", "latest", "Windows "},
             new Object[]{"internet explorer", "11.0", "Windows"},
              new Object[] {testNGCucumberRunner.provideScenarios()}
     };
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		if (testNGCucumberRunner == null) {
			return;
		}
		testNGCucumberRunner.finish();

	}
	
	

}
