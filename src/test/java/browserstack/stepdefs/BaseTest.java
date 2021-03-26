package browserstack.stepdefs;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.browserstack.local.Local;

import browserstack.utils.OsUtility;
import browserstack.utils.Utility;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.testng.TestNGCucumberRunner;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BaseTest {

	public static WebDriverWait wait;
	protected String chromeDriverBaseLocation = System.getProperty("user.dir") + "/src/test/resources/chromeDriver";
	private Local local;
	protected static String URL = "http://localhost:3000";
	public DesiredCapabilities caps= new DesiredCapabilities();
	private static String username;
	private static String accessKey;
	  private static final String PASSED = "passed";
	    private static final String FAILED = "failed";
	    private static final String REPO_NAME = "browserstack-examples-cucumber-testng - ";

	public WebDriver getDriver() {
		return ThreadLocalDriver.getWebDriver();
	}

	

	@BeforeMethod
	@Parameters({ "environment", "browser","test","env_cap_id","settestname"})
	public void setUpClass( @Optional("remote") String environment,
			@Optional("chrome") String browser,
			@Optional("single") String test, @Optional("3") int env_cap_id,@Optional("BStack test name") String settestname) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser
				.parse(new FileReader("src/test/resources/config/browserstack.conf.json"));
		URL = (String) config.get("application_endpoint");
		System.out.print("Scenarion name "+ Scenario.class.getName());
		if (environment.equalsIgnoreCase("local")) {
			if (OsUtility.isMac()) {
				System.setProperty("webdriver.chrome.driver",
						chromeDriverBaseLocation + "/chromeDriverMac/chromedriver");
			}
			if (OsUtility.isWindows()) {
				System.setProperty("webdriver.chrome.driver",
						chromeDriverBaseLocation + "/chromeDriverWin/chromedriver.exe");
			}
			if (OsUtility.isUnix()) {
				System.setProperty("webdriver.chrome.driver",
						chromeDriverBaseLocation + "/chromeDriverLinux/chromedriver");
			}
			ThreadLocalDriver.setWebDriver(new ChromeDriver());
			wait = new WebDriverWait(ThreadLocalDriver.getWebDriver(), 120);
		} else if (environment.equalsIgnoreCase("remote")) {
			JSONObject profilesJson = (JSONObject) config.get("tests");
			JSONObject envs = (JSONObject) profilesJson.get(test);

			Map<String, String> commonCapabilities = (Map<String, String>) envs.get("common_caps");
			Map<String, String> envCapabilities = (Map<String, String>) ((org.json.simple.JSONArray) envs
					.get("env_caps")).get(env_cap_id);
			Map<String, String> localCapabilities = (Map<String, String>) envs.get("local_binding_caps");

			 caps = new DesiredCapabilities();
			
			caps.merge(new DesiredCapabilities(commonCapabilities));
			caps.merge(new DesiredCapabilities(envCapabilities));
			if (test.equals("local")) {
				URL = (String) envs.get("application_endpoint");
				caps.merge(new DesiredCapabilities(localCapabilities));
			}

			 username = System.getenv("BROWSERSTACK_USERNAME");
			if (username == null) {
				username = (String) config.get("user").toString();
			}
			 accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
			if (accessKey == null) {
				accessKey = (String) config.get("key").toString();
			}

			if (caps.getCapability("browserstack.local") != null
					&& caps.getCapability("browserstack.local").equals("true")) {
				local = new Local();
				UUID uuid = UUID.randomUUID();
				caps.setCapability("browserstack.localIdentifier", uuid.toString());
				Map<String, String> options = new HashMap<String, String>();
				options.put("key", accessKey);
				options.put("localIdentifier", uuid.toString());
				local.start(options);
			}
		
			
			

		}
	}
	
	@Before
	public synchronized void startup(Scenario scenario)
	{
		 setBuildAndTestName(caps, scenario);
		try {
			ThreadLocalDriver.setWebDriver(new RemoteWebDriver(
					new URL("https://" + username + ":" + accessKey + "@hub.browserstack.com/wd/hub"), caps));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ThreadLocalDriver.getWebDriver().get(URL);
		wait = new WebDriverWait(ThreadLocalDriver.getWebDriver(), 120);
		ThreadLocalDriver.getWebDriver().manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		
	}
	
	 private void setBuildAndTestName(DesiredCapabilities caps, Scenario scenario) {
	        caps.setCapability("name", scenario.getName());
	        String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
	        if (buildName == null) {
	            buildName = Utility.getEpochTime();
	        }
	        caps.setCapability("build", REPO_NAME + buildName);
	    }


	 @After
	    public void teardown(Scenario scenario) throws Exception {
	      
	            if (scenario.isFailed()) {
	                Utility.setSessionStatus(ThreadLocalDriver.getWebDriver(), FAILED, String.format("%s failed.", scenario.getName()));
	            } else {
	                Utility.setSessionStatus(ThreadLocalDriver.getWebDriver(), PASSED, String.format("%s passed.", scenario.getName()));
	            }
	        
	        ThreadLocalDriver.getWebDriver().quit();
	       // if (bstackLocal != null)Ω bstackLocal.stop();
	    }


}
