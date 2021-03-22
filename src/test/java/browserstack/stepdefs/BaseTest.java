package browserstack.stepdefs;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.browserstack.local.Local;
import browserstack.utils.OsUtility;
import cucumber.api.testng.TestNGCucumberRunner;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BaseTest {

    public WebDriverWait wait;
    protected String chromeDriverBaseLocation = System.getProperty("user.dir")+ "/src/test/resources/chromeDriver";
    private Local local;
    private TestNGCucumberRunner testNGCucumberRunner;
	protected static String URL = "http://localhost:3000";

	  public WebDriver getDriver() {
	        return ThreadLocalDriver.getWebDriver();
	    }

    @BeforeMethod(alwaysRun = true)
	@Parameters({ "configfile", "environment", "browser", "url" })
	public void setUpClass(@Optional("browserstack.conf.json") String configfile, @Optional("local") String environment, @Optional("chrome") String browser, @Optional("http://localhost:3000") String url) throws Exception {

    	
		 if (environment.equalsIgnoreCase("local")) {
	            if (OsUtility.isMac()) {
	                System.setProperty("webdriver.chrome.driver", chromeDriverBaseLocation+ "/chromeDriverMac/chromedriver");
	            }
	            if (OsUtility.isWindows()) {
	                System.setProperty("webdriver.chrome.driver", chromeDriverBaseLocation+ "/chromeDriverWin/chromedriver.exe");
	            }
	            if (OsUtility.isUnix()) {
	                System.setProperty("webdriver.chrome.driver",chromeDriverBaseLocation+ "/chromeDriverLinux/chromedriver");
	            }
	            ThreadLocalDriver.setWebDriver(new ChromeDriver());
	        } else if (environment.equalsIgnoreCase("remote")) {
	            JSONParser parser = new JSONParser();
	            JSONObject config = (JSONObject) parser.parse(new FileReader("src/test/resources/config/" + configfile));
	            JSONObject envs = (JSONObject) config.get("environments");
	            DesiredCapabilities capabilities = new DesiredCapabilities();

	            Map<String, String> envCapabilities = (Map<String, String>) envs.get(browser);
	            Iterator it = envCapabilities.entrySet().iterator();
	            while (it.hasNext()) {
	                Map.Entry pair = (Map.Entry) it.next();
	                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
	            }

	            Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
	            it = commonCapabilities.entrySet().iterator();
	            while (it.hasNext()) {
	                Map.Entry pair = (Map.Entry) it.next();
	                if (capabilities.getCapability(pair.getKey().toString()) == null) {
	                    capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
	                }
	            }

	            String username = System.getenv("BROWSERSTACK_USERNAME");
	            if (username == null) {
	                username = (String) config.get("user").toString();
	            }

	            String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
	            if (accessKey == null) {
	                accessKey = (String) config.get("key").toString();
	            }

	            capabilities.setCapability("browserstack.appiumLogs", "false");
	            capabilities.setCapability("browserstack.seleniumLogs", "false");
	            capabilities.setCapability("browserstack.geoLocation", "IN");
	           
	            
	            if (capabilities.getCapability("browserstack.local") != null
	                    && capabilities.getCapability("browserstack.local") == "true") {
	                local = new Local();
	                UUID uuid = UUID.randomUUID();
	                capabilities.setCapability("browserstack.localIdentifier", uuid.toString());
	                Map<String, String> options = new HashMap<String, String>();
	                options.put("key", accessKey);
	                options.put("localIdentifier", uuid.toString());
	                local.start(options);
	            }

	            ThreadLocalDriver.setWebDriver(new RemoteWebDriver(new URL("https://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub"), capabilities));
	        } else if (environment.equalsIgnoreCase("docker")) {
	            DesiredCapabilities dc = new DesiredCapabilities();
	            dc.setBrowserName("chrome");
	            dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	            ThreadLocalDriver.setWebDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc));
	        }
		 ThreadLocalDriver.getWebDriver().get(url);
	        wait = new WebDriverWait(ThreadLocalDriver.getWebDriver(), 50);	
	        ThreadLocalDriver.getWebDriver().manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
	        ThreadLocalDriver.getWebDriver().manage().window().maximize();
	        URL =url;
	}

    @AfterMethod
    public synchronized void teardown(){
        ThreadLocalDriver.getWebDriver().quit();
    }

}
