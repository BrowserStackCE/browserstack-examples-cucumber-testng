package browserstack.stepdefs;

import org.openqa.selenium.JavascriptExecutor;
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

import browserstack.SingleTestRunner;
import browserstack.utils.Log;
import browserstack.utils.OsUtility;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.testng.TestNGCucumberRunner;


import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
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

	public static WebDriverWait wait;
	protected String chromeDriverBaseLocation = System.getProperty("user.dir") + "/src/test/resources/chromeDriver";
	private Local local;
	private TestNGCucumberRunner testNGCucumberRunner;
	protected static String URL = "http://localhost:3000";

	public WebDriver getDriver() {
		return ThreadLocalDriver.getWebDriver();
	}

	

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "environment", "browser","test","env_cap_id","settestname"})
	public void setUpClass( @Optional("local") String environment,
			@Optional("chrome") String browser,
			@Optional("single") String test, @Optional("0") int env_cap_id,@Optional("BStack test name") String settestname) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject config = (JSONObject) parser
				.parse(new FileReader("src/test/resources/config/browserstack.conf.json"));
		URL = (String) config.get("application_endpoint");
		
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

			DesiredCapabilities caps = new DesiredCapabilities();
			
			caps.merge(new DesiredCapabilities(commonCapabilities));
			caps.merge(new DesiredCapabilities(envCapabilities));
			if (test.equals("local")) {
				URL = (String) envs.get("application_endpoint");
				caps.merge(new DesiredCapabilities(localCapabilities));
			}

			String username = System.getenv("BROWSERSTACK_USERNAME");
			if (username == null) {
				username = (String) config.get("user").toString();
			}
			String accessKey = System.getenv("BROWSERSTACK_ACCESS_KEY");
			if (accessKey == null) {
				accessKey = (String) config.get("key").toString();
			}

			/*
			 * if (!getGeolocation().isEmpty()) {
			 * caps.setCapability("browserstack.geoLocation", getGeolocation()); }
			 */

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
			caps.setCapability("name",settestname);
			ThreadLocalDriver.setWebDriver(new RemoteWebDriver(
					new URL("https://" + username + ":" + accessKey + "@hub.browserstack.com/wd/hub"), caps));

			ThreadLocalDriver.getWebDriver().get(URL);
			wait = new WebDriverWait(ThreadLocalDriver.getWebDriver(), 120);
			ThreadLocalDriver.getWebDriver().manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			

		}
	}

	@AfterMethod
	public synchronized void teardown() {
		 ThreadLocalDriver.getWebDriver().quit();
	}
	


}
