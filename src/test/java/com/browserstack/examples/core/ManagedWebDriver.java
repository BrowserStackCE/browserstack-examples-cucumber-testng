package com.browserstack.examples.core;

import org.openqa.selenium.WebDriver;

import com.browserstack.examples.core.config.Platform;
import com.browserstack.examples.core.WebDriverFactory;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class ManagedWebDriver {

    private final WebDriverFactory webDriverFactory;
    private final Platform platform;
    private String testName;
    private WebDriver webDriver;

    public ManagedWebDriver(Platform platform, WebDriverFactory webDriverFactory) {
        this.platform = platform;
        this.webDriverFactory = webDriverFactory;
    }

    public void setTestName(String t) {
        this.testName = t;
    }

    public String getTestName() {
        return this.testName;
    }

    public WebDriver getWebDriver() {
        String[] specificCapabilities = new String[0];

        if (this.webDriver == null) {
            this.webDriver = this.webDriverFactory.createWebDriverForPlatform(platform, testName, specificCapabilities);
        }
        return this.webDriver;
    }
}
