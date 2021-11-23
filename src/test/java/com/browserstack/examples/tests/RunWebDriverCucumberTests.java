package com.browserstack.examples.tests;

import java.util.Iterator;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.browserstack.webdriver.testng.LazyInitWebDriverIterator;
import com.browserstack.webdriver.testng.ManagedWebDriver;
import com.browserstack.webdriver.testng.listeners.WebDriverListener;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
@CucumberOptions(
  features = "classpath:features",
  glue = "com.browserstack.examples.stepdefs",
  plugin = {
    "pretty",
    "html:reports/tests/cucumber/html",
    "timeline:reports/tests/cucumber/timeline",
    "junit:reports/tests/cucumber/junit/cucumber.xml",
    "testng:reports/tests/cucumber/testng/cucumber.xml",
    "json:reports/tests/cucumber/json/cucumber.json"
  }
)
@Listeners({WebDriverListener.class})
public class RunWebDriverCucumberTests {

    private TestNGCucumberRunner testNGCucumberRunner;
    private static final ThreadLocal<ManagedWebDriver> threadLocalWebDriver = new ThreadLocal<>();

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    private synchronized static void setThreadLocalWebDriver(ManagedWebDriver managedWebDriver) {
        threadLocalWebDriver.set(managedWebDriver);
    }

    public synchronized static ManagedWebDriver getManagedWebDriver() {
        return threadLocalWebDriver.get();
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "scenarios")
    public void feature(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper, ManagedWebDriver managedWebDriver) {
        managedWebDriver.setTestName(pickleWrapper.getPickle().getName());
        setThreadLocalWebDriver(managedWebDriver);
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider(name = "scenarios", parallel = true)
    public Iterator<Object[]> scenarios() {
        Object[][] scenarios = testNGCucumberRunner.provideScenarios();
        return new LazyInitWebDriverIterator(true, scenarios);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (testNGCucumberRunner == null) {
            return;
        }
        testNGCucumberRunner.finish();
    }

}
