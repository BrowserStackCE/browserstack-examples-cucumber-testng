package browserstack;

import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;

import org.testng.Reporter;
import org.testng.annotations.*;

import browserstack.stepdefs.BaseTest;
import browserstack.stepdefs.ThreadLocalDriver;
import browserstack.utils.AllureReportConfigurationSetup;
import browserstack.utils.DataHelper;


@CucumberOptions(features = "src/test/resources/com/browserstack/e2e.feature", glue = "browserstack.stepdefs", format = {
		"pretty", "html:target/cucumber-reports/cucumber-pretty",
		"json:target/cucumber-reports/CucumberTestReport.json", "rerun:target/cucumber-reports/rerun.txt", })
public class SingleTestRunner extends BaseTest {

	private TestNGCucumberRunner testNGCucumberRunner;
	public static String TestName = null;
	String featureName = null;
	CucumberFeatureWrapper cucumberFeature;

	@BeforeClass(alwaysRun = true)
	public void setUpClass() {
		AllureReportConfigurationSetup.prepareAllureResultsFolder();
		System.out.println("Cucumber Test Class Before");
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
		System.out.println(cucumberFeature.getCucumberFeature().toString());

	}

	@Test(groups = "cucumber", description = "Runs LoginCandiate Feature", dataProvider = "features")
	public void feature(CucumberFeatureWrapper cucumberFeature) {

		System.out.println("Cucumber Test Started");
		System.out.println(cucumberFeature);
		testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
		TestName = cucumberFeature.toString();

	}

	@DataProvider
	public Object[][] features() {
		System.out.println("Data Provider test Class");
		return testNGCucumberRunner.provideFeatures();
	}

	@AfterClass(alwaysRun = true)
	public void tearDownClass1() throws Exception {

		testNGCucumberRunner.finish();
	}

}
