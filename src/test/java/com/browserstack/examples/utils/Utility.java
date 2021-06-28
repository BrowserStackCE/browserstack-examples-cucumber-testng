package com.browserstack.examples.utils;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utility {

	private Utility() {
	}

	private static final String LOCATION_SCRIPT_FORMAT = "navigator.geolocation.getCurrentPosition = function(success){\n"
			+ "    var position = { \"coords\":{\"latitude\":\"%s\",\"longitude\":\"%s\"}};\n"
			+ "    success(position);\n" + "}";
	private static final String OFFER_LATITUDE = "19";
	private static final String OFFER_LONGITUDE = "75";

	private static Object SYNCHRONIZER = new Object();
	private static String epochTime = null;

	public static String getEpochTime() {
		if (epochTime == null) {
			synchronized (SYNCHRONIZER) {
				if (epochTime == null) {
					epochTime = String.valueOf(Instant.now().toEpochMilli());
				}
			}
		}
		return epochTime;
	}

	public static void setSessionStatus(WebDriver webDriver, String status, String reason) {
		JavascriptExecutor jse = (JavascriptExecutor) webDriver;
		jse.executeScript(String.format(
				"browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"%s\", \"reason\": \"%s\"}}",
				status, reason));
	}

	public static void mockGPS(WebDriver webDriver) {
		String locationScript = String.format(LOCATION_SCRIPT_FORMAT, OFFER_LATITUDE, OFFER_LONGITUDE);
		((JavascriptExecutor) webDriver).executeScript(locationScript);
	}

	public static boolean isAscendingOrder(List<WebElement> priceWebElement, int length) {
		if (priceWebElement == null || length < 2)
			return true;
		if (Integer.parseInt(priceWebElement.get(length - 2).getText()) > Integer
				.parseInt(priceWebElement.get(length - 1).getText()))
			return false;
		return isAscendingOrder(priceWebElement, length - 1);
	}

	public static boolean waitForJSLoad(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, 30);


		// wait for Javascript to load
		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState")
						.toString().equals("complete");
			}
		};
		return wait.until(jsLoad);
	}

	public static void moveFolder()
	{
		File from = new File(System.getProperty("user.dir")+"/allure-results");
        File to = new File(System.getProperty("user.dir")+"/target/"+"allure-results");

        try {
            FileUtils.copyDirectory(from, to);
            FileUtils.deleteDirectory(new File(System.getProperty("user.dir")+"/allure-results"));

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
	}
}
