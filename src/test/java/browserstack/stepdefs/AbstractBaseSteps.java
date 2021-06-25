package browserstack.stepdefs;

import org.openqa.selenium.WebDriver;

import browserstack.driverconfig.WebDriverFactory;
import browserstack.providers.WebDriverManager;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Anirudha Khanna
 */
public abstract class AbstractBaseSteps {

    private WebDriverManager webDriverManager;
    private WebDriver webDriver;

    public AbstractBaseSteps() {
        this.webDriverManager = WebDriverManager.getInstance();
    }

    public WebDriver getWebDriver() {
        if (this.webDriver == null) {
            this.webDriver = webDriverManager.getWebDriver();
        }
        return this.webDriver;
    }

    public String getTestEndpoint() {
        return WebDriverFactory.getInstance().getTestEndpoint();
    }
}
