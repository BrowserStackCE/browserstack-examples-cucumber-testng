package browserstack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import browserstack.driverconfig.Platform;
import browserstack.driverconfig.WebDriverFactory;
import browserstack.providers.ManagedWebDriver;
import browserstack.providers.WebDriverManager;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class RunWebDriverCucumberTests {

    private TestNGCucumberRunner testNGCucumberRunner;
    private WebDriverFactory webDriverFactory;
    private WebDriverManager webDriverManager;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
        this.webDriverFactory = WebDriverFactory.getInstance();
        this.webDriverManager = WebDriverManager.getInstance();
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
    public void feature(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper, ManagedWebDriver managedWebDriver) {
        managedWebDriver.setTestName(pickleWrapper.getPickle().getName());
        webDriverManager.setThreadLocalWebDriver(managedWebDriver);
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider(name = "scenarios", parallel = true)
    public Object[][] scenarios() {
        List<Object[]> browserScenarioParams = new ArrayList<>();

        Object[][] scenarios = testNGCucumberRunner.provideScenarios();
        for (Object[] testParams : scenarios) {
            for (Platform platform : webDriverFactory.getPlatforms()) {
                Object[] newTestParams = Arrays.copyOf(testParams, testParams.length + 1);
                ManagedWebDriver managedWebDriver = new ManagedWebDriver(platform, webDriverFactory);
                newTestParams[testParams.length] = managedWebDriver;
                browserScenarioParams.add(newTestParams);
            }
        }
        return browserScenarioParams.toArray(new Object[0][0]);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (testNGCucumberRunner == null) {
            return;
        }
        testNGCucumberRunner.finish();
    }

}
