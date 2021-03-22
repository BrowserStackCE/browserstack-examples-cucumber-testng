package browserstack.stepdefs;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import browserstack.utils.DataHelper;
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
    	ThreadLocalDriver.getWebDriver().findElement(By.linkText(linkText)).click();
    	Utility.waitforLoad(ThreadLocalDriver.getWebDriver());
    }
    

    @And("^I type \"([^\"]*)\" in \"([^\"]*)\" input$")
    public void iTypeInInput(String text, String inputName) {
    	ThreadLocalDriver.getWebDriver().findElement(By.id(inputName)).sendKeys(text);
    }
    
    
    

    @And("^I type \"([^\"]*)\" in \"([^\"]*)\"$")
    public void iTypeIn(String text,String inputName) throws InterruptedException {
    	
    	
    	
        if(inputName.equalsIgnoreCase("username")){
        	ThreadLocalDriver.getWebDriver().findElement(By.xpath("//*[@id=\"username\"]/div/div[1]")).click();
        	ThreadLocalDriver.getWebDriver().findElement(By.id("react-select-2-input")).sendKeys(text);
        	ThreadLocalDriver.getWebDriver().findElement(By.id("react-select-2-input")).sendKeys(Keys.ENTER);
        } else if (inputName.equalsIgnoreCase("password")) {
        	ThreadLocalDriver.getWebDriver().findElement(By.xpath("//*[@id=\"password\"]/div/div[1]")).click();
        	ThreadLocalDriver.getWebDriver().findElement(By.id("react-select-3-input")).sendKeys(text);
        	ThreadLocalDriver.getWebDriver().findElement(By.id("react-select-3-input")).sendKeys(Keys.ENTER);
        }
    }

   

    
   
}