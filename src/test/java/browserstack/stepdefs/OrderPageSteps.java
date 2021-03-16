package browserstack.stepdefs;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import browserstack.TestRunner;
import cucumber.api.java.en.Then;

public class OrderPageSteps extends TestRunner {

	//public ThreadLocal<WebDriver> driver = this.driver;

 

    @Then("I should see elements in list")
    public void iShouldSeeElementsInList() {
        WebElement element = null;
        try {
            element = driver.get().findElement(By.cssSelector("#__next > main > div > div > h2"));
            throw new AssertionError("There are no orders");
        } catch (NoSuchElementException e) {
        	Assert.assertNull(element);
        }
    }

}
