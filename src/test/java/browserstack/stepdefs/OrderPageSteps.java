package browserstack.stepdefs;


import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import cucumber.api.java.en.Then;

public class OrderPageSteps extends BaseTest {


    @Then("I should see elements in list")
    public void iShouldSeeElementsInList() {
        WebElement element = null;
        try {
            element = ThreadLocalDriver.getWebDriver().findElement(By.cssSelector("#__next > main > div > div > h2"));
            throw new AssertionError("There are no orders");
        } catch (NoSuchElementException e) {
        	Assert.assertNull(element);
        }
    }

}