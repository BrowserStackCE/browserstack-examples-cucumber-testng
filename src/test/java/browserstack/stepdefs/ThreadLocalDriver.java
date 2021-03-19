package browserstack.stepdefs;

import org.openqa.selenium.WebDriver;

public class ThreadLocalDriver {

    private static ThreadLocal<WebDriver> webdriver = new ThreadLocal<>();

    public synchronized static void setWebDriver(WebDriver driver) {
    	webdriver.set(driver);
    }

    public synchronized static WebDriver getWebDriver() {
        return webdriver.get();
    }
    
    

}
