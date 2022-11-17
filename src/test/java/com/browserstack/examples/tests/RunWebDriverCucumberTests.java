package com.browserstack.examples.tests;

import java.util.Iterator;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.cucumber.testng.*;


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

public class RunWebDriverCucumberTests extends AbstractTestNGCucumberTests {
}
