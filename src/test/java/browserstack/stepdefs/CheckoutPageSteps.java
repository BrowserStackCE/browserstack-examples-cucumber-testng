package browserstack.stepdefs;


import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import browserstack.utils.Utility;
import io.cucumber.java.en.And;


public class CheckoutPageSteps  {
	
	 @And("I type {string} in Post Code")
	    public void iTypeInPostCode(String postCode) {
	        ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".dynamic-form-field--postCode #provinceInput")).sendKeys(postCode);
	    }

	    @And("I click on Checkout Button")
	    public void iClickOnCheckoutButton() {
	        ThreadLocalDriver.getWebDriver().findElement(By.id("checkout-shipping-continue")).click();
	        WebDriverWait wait = new WebDriverWait(ThreadLocalDriver.getWebDriver(), 5);
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#checkout-app > div > div > div > div > a > button"))).click();
	    }

	    @And("I enter shipping details {string}, {string}, {string}, {string} and {string}")
	    public void iEnterShippingDetailsAnd(String first, String last, String address, String province, String postCode) {
	        WebDriverWait wait = new WebDriverWait(ThreadLocalDriver.getWebDriver(), 5);
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstNameInput"))).sendKeys(first);
	        ThreadLocalDriver.getWebDriver().findElement(By.id("lastNameInput")).sendKeys(last);
	        ThreadLocalDriver.getWebDriver().findElement(By.id("addressLine1Input")).sendKeys(address);
	        ThreadLocalDriver.getWebDriver().findElement(By.id("provinceInput")).sendKeys(province);
	        ThreadLocalDriver.getWebDriver().findElement(By.id("postCodeInput")).sendKeys(postCode);
	    }

}