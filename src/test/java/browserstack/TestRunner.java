package browserstack;

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

@CucumberOptions(features = "src/test/resources/com/browserstack", glue = "browserstack.stepdefs")
public class TestRunner   {
	
	private WebDriverFactory webDriverFactory = new WebDriverFactory();
	private Platform platform = new Platform();
	private WebDriver webDriver;
	
	

	private TestNGCucumberRunner testNGCucumberRunner;
	private String testMethodName;
	
	@BeforeSuite
	public void setupReport()
	{
		AllureReportConfigurationSetup.prepareAllureResultsFolder();
	}

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		
	}
	
	
	

	@BeforeMethod
	public WebDriver createDriver() throws MalformedURLException {
		String[] specificCapabilities = new String[0];
		if (this.webDriver == null) {
			this.webDriver = this.webDriverFactory.createWebDriverForPlatform(this.platform, 
this.testMethodName,
					specificCapabilities);
		}
		return this.webDriver;
	}

	

	@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
	public void feature(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
		testNGCucumberRunner.runScenario(pickleWrapper.getPickle());

	}

	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return testNGCucumberRunner.provideScenarios();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		if (testNGCucumberRunner == null) {
			return;
		}
		testNGCucumberRunner.finish();

	}
	
	

}
