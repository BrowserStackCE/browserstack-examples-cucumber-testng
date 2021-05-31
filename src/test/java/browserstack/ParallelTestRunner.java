package browserstack;

import org.testng.annotations.*;

import browserstack.stepdefs.BaseTest;
import browserstack.utils.AllureReportConfigurationSetup;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

@CucumberOptions(features = "src/test/resources/com/browserstack", glue = "browserstack.stepdefs")
public class ParallelTestRunner extends BaseTest {
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
