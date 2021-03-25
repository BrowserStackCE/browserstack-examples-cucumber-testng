package browserstack.stepdefs;


import java.sql.Driver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import browserstack.utils.Utility;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class CommonSteps extends BaseTest {

 
    @Given("^I navigate to website$")
    public void iNavigateToWebsite() {
    	ThreadLocalDriver.getWebDriver().get(BaseTest.URL);
    }

    @And("^I click on \"([^\"]*)\" link$")
    public void iClickOnLink(String linkText) throws InterruptedException {
    	 WebDriverWait wait = new WebDriverWait(ThreadLocalDriver.getWebDriver(), 60);
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText)));
         wait.until(ExpectedConditions.elementToBeClickable(By.linkText(linkText)));
         ThreadLocalDriver.getWebDriver().findElement(By.linkText(linkText)).click();
    }
    

    @And("^I type \"([^\"]*)\" in \"([^\"]*)\" input$")
    public void iTypeInInput(String text, String inputName) {
    	ThreadLocalDriver.getWebDriver().findElement(By.id(inputName)).sendKeys(text);
    }
    
    
    

    @And("^I type \"([^\"]*)\" in \"([^\"]*)\"$")
    public void iTypeIn(String text,String inputName) throws InterruptedException {
    	WebDriverWait wait = new WebDriverWait(ThreadLocalDriver.getWebDriver(), 5);
        if(inputName.equalsIgnoreCase("username")){
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#username > div > div:nth-child(1)"))).click();
            ThreadLocalDriver.getWebDriver().findElement(By.id("react-select-2-input")).sendKeys(text);
            ThreadLocalDriver.getWebDriver().findElement(By.id("react-select-2-input")).sendKeys(Keys.ENTER);
        } else if (inputName.equalsIgnoreCase("password")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#password > div > div:nth-child(1)"))).click();
            ThreadLocalDriver.getWebDriver().findElement(By.id("react-select-3-input")).sendKeys(text);
            ThreadLocalDriver.getWebDriver().findElement(By.id("react-select-3-input")).sendKeys(Keys.ENTER);
        }
    }
    

   
}