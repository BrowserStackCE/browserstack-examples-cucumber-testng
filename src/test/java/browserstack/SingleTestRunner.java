	package browserstack;
	
	import io.cucumber.java.Before;
	import io.cucumber.java.Scenario;
	import io.cucumber.testng.*;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.DataProvider;
	import org.testng.annotations.Test;
	
	import browserstack.stepdefs.BaseTest;
import browserstack.utils.AllureReportConfigurationSetup;
	
	@CucumberOptions(features = { "src/test/resources/com/browserstack" }, glue = {
			"browserstack.stepdefs" })
	public class SingleTestRunner extends BaseTest{
		private TestNGCucumberRunner testNGCucumberRunner;
	
		@BeforeClass(alwaysRun = true)
		public void setUpClass() {
			testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
			AllureReportConfigurationSetup.prepareAllureResultsFolder();
	
		}
	
		@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
		public void feature(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
			testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
	
		}
	
		@DataProvider
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