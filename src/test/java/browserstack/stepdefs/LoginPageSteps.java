package browserstack.stepdefs;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
	
	 @And("^I SignIn as \"([^\"]*)\" with \"([^\"]*)\" password$")
	    public void iSignInAsWithPassword(String username, String password) {
	        WebDriverWait wait = new WebDriverWait(ThreadLocalDriver.getWebDriver(), 10);
	        ThreadLocalDriver.getWebDriver().findElement(By.linkText("Sign In")).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#username > div > div:nth-child(1)"))).click();
	        ThreadLocalDriver.getWebDriver().findElement(By.id("react-select-2-input")).sendKeys(username);
	        ThreadLocalDriver.getWebDriver().findElement(By.id("react-select-2-input")).sendKeys(Keys.ENTER);
	        ThreadLocalDriver.getWebDriver().findElement(By.cssSelector("#password > div > div:nth-child(1)")).click();
	        ThreadLocalDriver.getWebDriver().findElement(By.id("react-select-3-input")).sendKeys(password);
	        ThreadLocalDriver.getWebDriver().findElement(By.id("react-select-3-input")).sendKeys(Keys.ENTER);
	        ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".Button_root__24MxS")).click();
	        Utility.mockGPS(ThreadLocalDriver.getWebDriver());
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