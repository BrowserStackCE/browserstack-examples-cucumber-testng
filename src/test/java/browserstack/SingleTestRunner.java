	package browserstack;
	
	import io.cucumber.java.Before;
	import io.cucumber.java.Scenario;
	import io.cucumber.testng.*;
	import org.testng.annotations.AfterClass;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.DataProvider;
	import org.testng.annotations.Test;
	
	import browserstack.stepdefs.BaseTest;
	
	@CucumberOptions(features = { "src/test/resources/com/browserstack/login.feature" }, glue = {
			"browserstack.stepdefs" })
	
	public class SingleTestRunner extends BaseTest{
		private TestNGCucumberRunner testNGCucumberRunner;
	
		@BeforeClass(alwaysRun = true)
		public void setUpClass() {
			testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	
		}
	
		@Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
		public void feature(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
			System.out.println(" I am in Testng");
			testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
	
		}
	
		@DataProvider
		public Object[][] scenarios() {
			System.out.println(" I am in Data Provder");
			return testNGCucumberRunner.provideScenarios();
		}
	
		@AfterClass(alwaysRun = true)
		public void tearDownClass() {
			if (testNGCucumberRunner == null) {
				return;
			}
			testNGCucumberRunner.finish();
	
		}
	
		@Before
		public void setUp(Scenario scenario) throws Exception {
	
			System.out.println("scenario nanme" + scenario);
		}
	}