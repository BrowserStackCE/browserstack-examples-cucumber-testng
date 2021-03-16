package browserstack.stepdefs;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import browserstack.TestRunner;
import cucumber.api.java.en.Then;

public class OfferPageSteps extends TestRunner {

	//public ThreadLocal<WebDriver> driver = this.driver; 
    @Then("I should see Offer elements")
    public void iShouldSeeOfferElements() {
        try {
            WebElement element = driver.get().findElement(By.cssSelector(".pt-6"));
            Assert.assertNotNull(element);
        } catch (NoSuchElementException e) {
            throw new AssertionError("There are no offers");
        }
    }

}
