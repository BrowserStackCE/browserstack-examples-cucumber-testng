package browserstack;


import cucumber.api.CucumberOptions;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.api.testng.TestNGCucumberRunner;
import org.testng.annotations.*;

import browserstack.stepdefs.BaseTest;
import browserstack.utils.AllureReportConfigurationSetup;
import browserstack.utils.DataHelper;


@Listeners(browserstack.utils.BrowserstackTestStatusListener.class)	
@CucumberOptions(
        features = "src/test/resources/com/browserstack/login.feature",
        glue = "browserstack.stepdefs" ,
        format = {
                "pretty",
                "html:target/cucumber-reports/cucumber-pretty",
                "json:target/cucumber-reports/CucumberTestReport.json",
                "rerun:target/cucumber-reports/rerun.txt",
                }
                )
public class SingleTestRunner extends BaseTest   {

    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
    	AllureReportConfigurationSetup.prepareAllureResultsFolder();
        System.out.println("Cucumber Test Class Before");
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
       
      
    }

    @Test(groups = "cucumber", description = "Runs LoginCandiate Feature", dataProvider = "features")
    public void feature(CucumberFeatureWrapper cucumberFeature) {
    	
        System.out.println("Cucumber Test Started");
        System.out.println(Scenario.class.getName());
        System.out.println(cucumberFeature.getCucumberFeature());
        testNGCucumberRunner.runCucumber(cucumberFeature.getCucumberFeature());
    }

    @DataProvider
    public Object[][] features() {
        System.out.println("Data Provider test Class");
        
        //return new Object[][] { { "data one" }, { "data two" } };
     
        return testNGCucumberRunner.provideFeatures();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() throws Exception {
        testNGCucumberRunner.finish();
        
    }
    

}
