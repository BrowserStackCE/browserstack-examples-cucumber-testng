package browserstack.providers;

import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;

import browserstack.driverconfig.Platform;
import browserstack.driverconfig.WebDriverFactory;
import lombok.Getter;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
@Getter
public class ManagedWebDriver {

    private final String testMethodName;
    private final WebDriverFactory webDriverFactory;
    private final Platform platform;
    private WebDriver webDriver;

    public ManagedWebDriver(String testMethodName, Platform platform, WebDriverFactory webDriverFactory) {
        this.testMethodName = testMethodName;
        this.platform = platform;
        this.webDriverFactory = webDriverFactory;
    }

    public WebDriver getWebDriver() throws MalformedURLException {
    	 String[] specificCapabilities = new String[0];
        
        if (this.webDriver == null) {
            this.webDriver = this.webDriverFactory.createWebDriverForPlatform(platform, testMethodName,specificCapabilities);
        }
        return this.webDriver;
    }
}
