package browserstack.stepdefs;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import browserstack.TestRunner;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;

public class CommonSteps extends TestRunner {

	//public ThreadLocal<WebDriver> driver = this.driver;

 
    @Given("^I navigate to website$")
    public void iNavigateToWebsite() {
    	driver.get().get(TestRunner.URL);
    }

    @And("^I click on \"([^\"]*)\" link$")
    public void iClickOnLink(String linkText) throws InterruptedException {
    	driver.get().findElement(By.linkText(linkText)).click();
        Thread.sleep(2000);
    }
    

    @And("^I type \"([^\"]*)\" in \"([^\"]*)\" input$")
    public void iTypeInInput(String text, String inputName) {
    	driver.get().findElement(By.id(inputName)).sendKeys(text);
    }
    
    
    

    @And("^I type \"([^\"]*)\" in \"([^\"]*)\"$")
    public void iTypeIn(String text,String inputName) throws InterruptedException {
        if(inputName.equalsIgnoreCase("username")){
        	driver.get().findElement(By.xpath("//*[@id=\"username\"]/div/div[1]")).click();
        	driver.get().findElement(By.id("react-select-2-input")).sendKeys(text);
        	driver.get().findElement(By.id("react-select-2-input")).sendKeys(Keys.ENTER);
        } else if (inputName.equalsIgnoreCase("password")) {
        	driver.get().findElement(By.xpath("//*[@id=\"password\"]/div/div[1]")).click();
        	driver.get().findElement(By.id("react-select-3-input")).sendKeys(text);
        	driver.get().findElement(By.id("react-select-3-input")).sendKeys(Keys.ENTER);
        }
    }

   

    
   
}
