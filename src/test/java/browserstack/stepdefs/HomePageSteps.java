package browserstack.stepdefs;


import static org.testng.Assert.assertTrue;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import browserstack.utils.Utility;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class HomePageSteps extends BaseTest {


    @And("^I add two products to cart$")
    
    public void iAddProductsToCart() throws InterruptedException {
  
    	 wait = new WebDriverWait(ThreadLocalDriver.getWebDriver(), 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#\\31 > .shelf-item__buy-btn"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.float-cart__close-btn"))).click();
        ThreadLocalDriver.getWebDriver().findElement(By.cssSelector("#\\32 > .shelf-item__buy-btn")).click();
    }

    @And("^I click on Buy Button$")
    public void iClickOnBuyButton() throws InterruptedException {
    	wait.until(ExpectedConditions.visibilityOf(ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".buy-btn"))));
      ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".buy-btn")).click();
      Utility.waitforLoad(ThreadLocalDriver.getWebDriver());
    }

    @And("^I press the Apple Vendor Filter$")
    public void iPressTheAppleVendorFilter() throws InterruptedException {
    	wait.until(ExpectedConditions.visibilityOf(ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".filters-available-size:nth-child(2) .checkmark"))));
      ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".filters-available-size:nth-child(2) .checkmark")).click();
      Utility.waitforLoad(ThreadLocalDriver.getWebDriver());
    }

    @And("^I order by lowest to highest$")
    public void iOrderByLowestToHighest() throws InterruptedException {
        WebElement dropdown =ThreadLocalDriver.getWebDriver().findElement(By.cssSelector("select"));
        dropdown.findElement(By.xpath("//option[. = 'Lowest to highest']")).click();
        Utility.waitforLoad(ThreadLocalDriver.getWebDriver());
    }

    @Then("^I should see user \"([^\"]*)\" logged in$")
    public void iShouldUserLoggedIn(String user) {
        try {
            String loggedInUser =ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".username")).getText();
            Assert.assertEquals(user, loggedInUser);
        } catch (NoSuchElementException e) {
            throw new AssertionError(user+" is not logged in");
        }
    }

    @Then("^I should see no image loaded$")
    public void iShouldSeeNoImageLoaded() {
        try {
        	
        	wait = new WebDriverWait(ThreadLocalDriver.getWebDriver(), 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@alt='iPhone 12']")));
            String src =ThreadLocalDriver.getWebDriver().findElement(By.xpath("//img[@alt='iPhone 12']")).getAttribute("src");
            Assert.assertEquals("", src);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in logging in");
        }
    }

    @Then("I should see (\\d+) items in the list")
    public void iShouldSeeItemsInTheList(int productCount) {
        try{
            String products =ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".products-found > span")).getText();
            Assert.assertEquals(products,productCount+" Product(s) found.");
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in page load");
        }
    }

    @Then("I should see prices in ascending order")
    public void iShouldSeePricesInAscendingOrder() {
        try {
            int secondElementPrice = Integer.parseInt(ThreadLocalDriver.getWebDriver().findElement(By.cssSelector("#\\32 5 .val > b")).getText());
            int firstElementPrice = Integer.parseInt(ThreadLocalDriver.getWebDriver().findElement(By.cssSelector("#\\31 9 .val > b")).getText());
            Assert.assertTrue(secondElementPrice>firstElementPrice);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in page load");
        }
    }
    
    @Then("I should be able to add items to favourites")
    public void clickOnFavourites() {
        try {
        	Thread.sleep(2000);
        	WebDriverWait wait = new WebDriverWait(ThreadLocalDriver.getWebDriver(), 60);
        	Random rand = new Random(); //instance of random class
            int upperbound = ThreadLocalDriver.getWebDriver().findElements(By.xpath("//span[@class='MuiIconButton-label']")).size(); 
            int int_random = rand.nextInt(upperbound-1); 
            JavascriptExecutor js = (JavascriptExecutor)ThreadLocalDriver.getWebDriver();	
            js.executeScript("arguments[0].click();",  ThreadLocalDriver.getWebDriver().findElement(By.xpath("(//div[@class='shelf-stopper']/button)["+int_random+"]")) );
            int_random = rand.nextInt(upperbound-1); 
            js.executeScript("arguments[0].click();",  ThreadLocalDriver.getWebDriver().findElement(By.xpath("(//div[@class='shelf-stopper']/button)["+int_random+"]")) );
             Utility.waitforLoad(ThreadLocalDriver.getWebDriver());
           ThreadLocalDriver.getWebDriver().findElement(By.linkText("Favourites")).click();	
            assertTrue(ThreadLocalDriver.getWebDriver().findElements(By.xpath("//div[@class='shelf-stopper']")).size()>0);
    
        } catch (NoSuchElementException | InterruptedException e) {
            throw new AssertionError("Error in page load");
        }
    }
    
 

}