package com.browserstack.examples.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openqa.selenium.WebDriver;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public class WebDriverManager {

    private static WebDriverManager instance;

    private final ThreadLocal<ManagedWebDriver> threadLocalWebDriver;
    private final Map<String, ManagedWebDriver> allocatedWebDrivers;

    private WebDriverManager() {
        threadLocalWebDriver = new ThreadLocal<>();
        allocatedWebDrivers = new ConcurrentHashMap<>();
    }

    public static WebDriverManager getInstance() {
        if (instance == null) {
            synchronized (WebDriverManager.class) {
                if (instance == null) {
                    instance = new WebDriverManager();
                }
            }
        }
        return instance;
    }

    public void setThreadLocalWebDriver(ManagedWebDriver managedWebDriver) {
        this.threadLocalWebDriver.set(managedWebDriver);
        this.allocatedWebDrivers.put(Thread.currentThread().getName(), managedWebDriver);
    }

    public WebDriver getWebDriver() {
        return this.threadLocalWebDriver.get().getWebDriver();
    }

    public void quitDriver() {
        String threadName = Thread.currentThread().getName();
        WebDriver webDriver = this.threadLocalWebDriver.get().getWebDriver();
        if (webDriver != null) {
            webDriver.quit();
        }
        ManagedWebDriver managedWebDriver = this.allocatedWebDrivers.remove(threadName);
    }

}
