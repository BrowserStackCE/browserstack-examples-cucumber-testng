package browserstack.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import browserstack.stepdefs.BaseTest;
import browserstack.stepdefs.ThreadLocalDriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class BrowserstackTestStatusListener implements ITestListener {

	public static void markTestStatus(String status, String reason, WebDriver driver) {
		try {
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \""
					+ status + "\", \"reason\": \"" + reason + "\"}}");
		} catch (Exception e) {
			Log.ERROR("Error executing javascript");
		}
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		markTestStatus("Passed", "", ThreadLocalDriver.getWebDriver());
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		markTestStatus("Failed", "", ThreadLocalDriver.getWebDriver());

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}
