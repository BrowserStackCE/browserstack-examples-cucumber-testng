package com.browserstack.examples.stepdefs;

import org.openqa.selenium.WebDriver;

import com.browserstack.examples.tests.RunWebDriverCucumberTests;
import com.browserstack.webdriver.core.WebDriverFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public abstract class AbstractBaseSteps {

    private WebDriver webDriver;

    public AbstractBaseSteps() {
    }

    public WebDriver getWebDriver() {
        if (this.webDriver == null) {
            this.webDriver = RunWebDriverCucumberTests.getManagedWebDriver().getWebDriver();
        }
        return this.webDriver;
    }

    public String getTestEndpoint() {
        return WebDriverFactory.getInstance().getTestEndpoint();
    }
}
