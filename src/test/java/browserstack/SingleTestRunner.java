package browserstack;

import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;


import org.testng.annotations.*;
import browserstack.stepdefs.BaseTest;
import browserstack.utils.AllureReportConfigurationSetup;




@CucumberOptions(features = "src/test/resources/com/browserstack", glue = "browserstack.stepdefs", format = {
		"pretty", "html:target/cucumber-reports/cucumber-pretty",
		"json:target/cucumber-reports/CucumberTestReport.json", "rerun:target/cucumber-reports/rerun.txt", })
public class SingleTestRunner extends BaseTest {
	private TestNGCucumberRunner testNGCucumberRunner;
	
	@BeforeClass(alwaysRun = true)
	public void setUpClass() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		AllureReportConfigurationSetup.prepareAllureResultsFolder();
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}
	

	@Test(groups = "cucumber", description = "Runs LoginCandiate Feature", dataProvider = "features")
	public void feature(CucumberFeatureWrapper cucumberFeature) {
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
	}

	@DataProvider
	public Object[][] features() {
		return testNGCucumberRunner.provideFeatures();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass1() throws Exception {

		testNGCucumberRunner.finish();
	}
	
	
}
