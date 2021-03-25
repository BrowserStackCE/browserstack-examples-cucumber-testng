package browserstack;


import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import cucumber.runtime.RuntimeOptions;
import cucumber.runtime.RuntimeOptionsFactory;
import jdk.internal.org.jline.utils.Log;

import org.testng.annotations.*;
import browserstack.stepdefs.BaseTest;
import browserstack.utils.AllureReportConfigurationSetup;
import browserstack.utils.Utility;


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
        RuntimeOptionsFactory runtimeOptionsFactory = new RuntimeOptionsFactory(this.getClass());
  	  RuntimeOptions runtimeoptions = runtimeOptionsFactory.create();
  	 testName =Utility.TrimText(runtimeoptions.getFeaturePaths().get(0));
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
