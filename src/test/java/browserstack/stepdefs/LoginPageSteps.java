package browserstack.stepdefs;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import browserstack.TestRunner;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class LoginPageSteps extends TestRunner {

	@And("^I press Log In Button$")
	public void iPressLogin() throws InterruptedException {
		driver.get().findElement(By.cssSelector(".Button_root__24MxS")).click();
		Thread.sleep(3000);
	}

	@Then("^I should see \"([^\"]*)\" as Login Error Message$")
	public void iShouldSeeAsLoginErrorMessage(String expectedMessage) {
		try {
			String errorMessage = driver.get().findElement(By.cssSelector(".api-error")).getText();
			Assert.assertEquals(expectedMessage, errorMessage);
		} catch (NoSuchElementException e) {
			throw new AssertionError("Error in logging in");
		}
	}

	@Then("I click on Logout")
	public void iShouldbeAbleToLogout() {
		try {
			if (driver.get().findElement(By.linkText("Logout")).isDisplayed()) {
				driver.get().findElement(By.linkText("Logout")).click();
			}
		} catch (NoSuchElementException e) {
				e.printStackTrace();
		}
	}

}
