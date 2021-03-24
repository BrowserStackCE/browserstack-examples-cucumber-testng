package browserstack.stepdefs;


import org.openqa.selenium.By;

import browserstack.utils.Utility;
import cucumber.api.java.en.And;

public class CheckoutPageSteps extends BaseTest{
	
    @And("I type \"([^\"]*)\" in Post Code")
    public void iTypeInPostCode(String postCode) {
        
    	ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".dynamic-form-field--postCode #provinceInput")).sendKeys(postCode);
    
    }

    @And("I click on Checkout Button")
    public void iClickOnCheckoutButton() throws InterruptedException {
    	ThreadLocalDriver.getWebDriver().findElement(By.id("checkout-shipping-continue")).click();
    	Utility.waitforLoad(ThreadLocalDriver.getWebDriver());
    	Utility.waitforElement(ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".button")));
        ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".button")).click();
        Utility.waitforLoad(ThreadLocalDriver.getWebDriver());
    }

}