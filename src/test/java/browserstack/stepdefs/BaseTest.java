package browserstack.stepdefs;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.browserstack.local.Local;
import browserstack.utils.OsUtility;
import browserstack.utils.Utility;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BaseTest {

	public static WebDriverWait wait;
	protected String driverBaseLocation = System.getProperty("user.dir") + "/src/test/resources";
	public static Local local;
	public static Map<String, String> options;
	protected static String URL = "https://bstackdemo.com";
	public static DesiredCapabilities caps = new DesiredCapabilities();
	private static String username;
	private static String accessKey;
	private static final String PASSED = "passed";
	private static final String FAILED = "failed";
	private static final String REPO_NAME = "browserstack_examples_cucumber_testng_";
	private static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
	public static String env = "";
	public static JSONObject config;
	public static ThreadLocal<Map<String, String>> envCapabilities = new ThreadLocal<Map<String, String>>();

	public WebDriver getDriver() {
		return ThreadLocalDriver.getWebDriver();
	}

	@BeforeSuite
	public void before() throws Exception {
		
		
		
		if (System.getProperty("application_endpoint") != null && System.getProperty("application_endpoint").equals("http://localhost:3000/")) 
		{
			local = new Local();
			options = new HashMap<String, String>();
			accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
			options.put("key", accessKey);
			local.start(options);
		
		}
	}

	@BeforeMethod
	@Parameters({ "environment", "browser", "caps_type", "env_cap_id", "settestname" })
	public synchronized void setUpClass(@Optional("local") String environment, @Optional("chrome") String browser,
			@Optional("single") String caps_type, @Optional("2") int env_cap_id,
			@Optional("BStack test name") String settestname) throws Exception {
		JSONParser parser = new JSONParser();
		if (System.getProperty("application_endpoint") != null) {
			URL = System.getProperty("application_endpoint");
		}
		config = (JSONObject) parser.parse(new FileReader("src/test/resources/config/bs.json"));
		if (environment.equalsIgnoreCase("local")) {
			if (OsUtility.isWindows()) {
				System.setProperty(WEBDRIVER_CHROME_DRIVER,
						Paths.get(driverBaseLocation, "/chromedriver.exe").toString());
			} else {
				System.setProperty(WEBDRIVER_CHROME_DRIVER, Paths.get(driverBaseLocation, "/chromedriver").toString());
			}
			ThreadLocalDriver.setWebDriver(new ChromeDriver());

		}

		else if (environment.equalsIgnoreCase("docker")) {
			DesiredCapabilities dc = new DesiredCapabilities();
			dc.setBrowserName("chrome");
			dc.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			ThreadLocalDriver.setWebDriver(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc));
			
		} else if (environment.equalsIgnoreCase("remote")) {

			env = "remote";
			JSONObject profilesJson = (JSONObject) config.get("tests");
			JSONObject envs = (JSONObject) profilesJson.get(caps_type);
			Map<String, String> commonCapabilities = (Map<String, String>) envs.get("common_caps");
			envCapabilities
					.set((Map<String, String>) ((org.json.simple.JSONArray) envs.get("env_caps")).get(env_cap_id));

			Map<String, String> localCapabilities = (Map<String, String>) envs.get("local_binding_caps");

			caps.merge(new DesiredCapabilities(commonCapabilities));
			
			if (caps_type.equals("local")) {
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

		}
	}

	@Before
	public void startup(Scenario scenario) throws Exception {
		if (env == "remote") {
			DesiredCapabilities derivedCaps = new DesiredCapabilities();
			derivedCaps.merge(caps);
			derivedCaps.merge(new DesiredCapabilities()).setCapability("name", scenario.getName());
			String buildName = System.getenv("BROWSERSTACK_BUILD_NAME");
			if (buildName == null) {
				buildName =REPO_NAME + Utility.getEpochTime();
			}
			derivedCaps.setCapability("build", buildName);
			try {
				ThreadLocal<DesiredCapabilities> dc = new ThreadLocal<DesiredCapabilities>();
				dc.set(derivedCaps);
				dc.get().merge(new DesiredCapabilities(envCapabilities.get()));
				ThreadLocalDriver.setWebDriver(new RemoteWebDriver(
						new URL("https://" + username + ":" + accessKey + "@hub.browserstack.com/wd/hub"), dc.get()));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		ThreadLocalDriver.getWebDriver().get(URL);
		wait = new WebDriverWait(ThreadLocalDriver.getWebDriver(), 120);
		ThreadLocalDriver.getWebDriver().manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);

	}

	@After
	public void teardown(Scenario scenario) throws Exception {
		if (System.getProperty("environment").equalsIgnoreCase("remote")) {
			if (scenario.isFailed()) {
				Utility.setSessionStatus(ThreadLocalDriver.getWebDriver(), FAILED,
						String.format("%s failed.", scenario.getName()));

			} else {
				Utility.setSessionStatus(ThreadLocalDriver.getWebDriver(), PASSED,
						String.format("%s passed.", scenario.getName()));

			}

		}
		ThreadLocalDriver.getWebDriver().quit();

	}

	@AfterSuite
	public void tearDown() throws Exception {

		if (local != null) {
			local.stop();
		}
		Utility.moveFolder();

	}

}
