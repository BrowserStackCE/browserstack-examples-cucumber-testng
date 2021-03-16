package browserstack.stepdefs;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import browserstack.TestRunner;
import bsh.This;
import cucumber.api.java.en.And;

public class CheckoutPageSteps extends TestRunner{

	//public WebDriver driver = getDriver();
	
    @And("I type \"([^\"]*)\" in Post Code")
    public void iTypeInPostCode(String postCode) {
    	driver.get().findElement(By.cssSelector(".dynamic-form-field--postCode #provinceInput")).sendKeys(postCode);
    }

    @And("I click on Checkout Button")
    public void iClickOnCheckoutButton() throws InterruptedException {
    	driver.get().findElement(By.id("checkout-shipping-continue")).click();
        Thread.sleep(1500);
        driver.get().findElement(By.cssSelector(".button")).click();
        Thread.sleep(1000);
    }

}
