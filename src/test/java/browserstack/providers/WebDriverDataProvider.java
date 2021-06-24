package browserstack.providers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import browserstack.driverconfig.Platform;
import browserstack.driverconfig.WebDriverFactory;



public class WebDriverDataProvider {

    @DataProvider(name = "webDriverProvider", parallel = true)
    public static Object[] provideWebDrivers(Method testMethod) {
        WebDriverFactory webDriverFactory = WebDriverFactory.getInstance();
        final List<ManagedWebDriver> managedWebDrivers = new ArrayList<>();
        List<Platform> platforms = webDriverFactory.getPlatforms();
        platforms.forEach( p -> {
            managedWebDrivers.add(new ManagedWebDriver(testMethod.getName(), p, webDriverFactory));
        });

        return managedWebDrivers.toArray(new ManagedWebDriver[0]);
    }
}
