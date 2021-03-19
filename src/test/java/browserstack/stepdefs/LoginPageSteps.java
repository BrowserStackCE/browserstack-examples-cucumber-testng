package browserstack.stepdefs;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;

import browserstack.utils.Utility;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class LoginPageSteps extends BaseTest {

	@And("^I press Log In Button$")
	public void iPressLogin() throws InterruptedException {
		ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".Button_root__24MxS")).click();
		Utility.waitforLoad(ThreadLocalDriver.getWebDriver());
	}

	@Then("^I should see \"([^\"]*)\" as Login Error Message$")
	public void iShouldSeeAsLoginErrorMessage(String expectedMessage) {
		try {
			String errorMessage = ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".api-error")).getText();
			Assert.assertEquals(expectedMessage, errorMessage);
		} catch (NoSuchElementException e) {
			throw new AssertionError("Error in logging in");
		}
	}

	@Then("I click on Logout")
	public void iShouldbeAbleToLogout() {
		try {
			if (ThreadLocalDriver.getWebDriver().findElement(By.linkText("Logout")).isDisplayed()) {
				ThreadLocalDriver.getWebDriver().findElement(By.linkText("Logout")).click();
			}
		} catch (NoSuchElementException e) {
				e.printStackTrace();
		}
	}

}