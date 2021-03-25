package browserstack;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.*;
import browserstack.stepdefs.BaseTest;
import browserstack.utils.AllureReportConfigurationSetup;



@CucumberOptions(
        features = "src/test/resources/com/browserstack",
        glue = "browserstack.stepdefs"

                )
public class ParallelTestRunner extends BaseTest   {

    private TestNGCucumberRunner testNGCucumberRunner;
    public static String testName = null;;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        AllureReportConfigurationSetup.prepareAllureResultsFolder();
    
    }

    @Test(groups = "cucumber", description = "Runs LoginCandiate Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
        
    }
    

    @DataProvider(parallel = true)	
    public Object[][] features() {
        return testNGCucumberRunner.provideFeatures();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
    	 testNGCucumberRunner.finish();
     }
      

}
