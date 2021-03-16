package browserstack.stepdefs;


import static org.testng.Assert.assertTrue;

import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import browserstack.TestRunner;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class HomePageSteps extends TestRunner {

	//public ThreadLocal<WebDriver> driver = this.driver;

    @And("^I add two products to cart$")
    public void iAddProductsToCart() throws InterruptedException {
    	driver.get().findElement(By.cssSelector("#\\31 > .shelf-item__buy-btn")).click();
        Thread.sleep(1000);
       driver.get().findElement(By.cssSelector("#__next > div > div > div.float-cart.float-cart--open > div.float-cart__close-btn")).click();
       driver.get().findElement(By.cssSelector("#\\32 > .shelf-item__buy-btn")).click();
        Thread.sleep(1000);
    }

    @And("^I click on Buy Button$")
    public void iClickOnBuyButton() throws InterruptedException {
       driver.get().findElement(By.cssSelector(".buy-btn")).click();
        Thread.sleep(1000);
    }

    @And("^I press the Apple Vendor Filter$")
    public void iPressTheAppleVendorFilter() throws InterruptedException {
       driver.get().findElement(By.cssSelector(".filters-available-size:nth-child(2) .checkmark")).click();
        Thread.sleep(1000);
    }

    @And("^I order by lowest to highest$")
    public void iOrderByLowestToHighest() throws InterruptedException {
        WebElement dropdown =driver.get().findElement(By.cssSelector("select"));
        dropdown.findElement(By.xpath("//option[. = 'Lowest to highest']")).click();
        Thread.sleep(1000);
    }

    @Then("^I should see user \"([^\"]*)\" logged in$")
    public void iShouldUserLoggedIn(String user) {
        try {
            String loggedInUser =driver.get().findElement(By.cssSelector(".username")).getText();
            Assert.assertEquals(user, loggedInUser);
        } catch (NoSuchElementException e) {
            throw new AssertionError(user+" is not logged in");
        }
    }

    @Then("^I should see no image loaded$")
    public void iShouldSeeNoImageLoaded() {
        try {
            String src =driver.get().findElement(By.xpath("//img[@alt='iPhone 12']")).getAttribute("src");
            Assert.assertEquals("", src);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in logging in");
        }
    }

    @Then("I should see (\\d+) items in the list")
    public void iShouldSeeItemsInTheList(int productCount) {
        try{
            String products =driver.get().findElement(By.cssSelector(".products-found > span")).getText();
            Assert.assertEquals(products,productCount+" Product(s) found.");
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in page load");
        }
    }

    @Then("I should see prices in ascending order")
    public void iShouldSeePricesInAscendingOrder() {
        try {
            int secondElementPrice = Integer.parseInt(driver.get().findElement(By.cssSelector("#\\32 5 .val > b")).getText());
            int firstElementPrice = Integer.parseInt(driver.get().findElement(By.cssSelector("#\\31 9 .val > b")).getText());
            Assert.assertTrue(secondElementPrice>firstElementPrice);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in page load");
        }
    }
    
    @Then("I should be able to add items to favourites")
    public void clickOnFavourites() {
        try {
        	
        	Random rand = new Random(); //instance of random class
            int upperbound =  driver.get().findElements(By.xpath("//span[@class='MuiIconButton-label']")).size(); 
            int int_random = rand.nextInt(upperbound-1); 
            driver.get().findElement(By.xpath("(//span[@class='MuiIconButton-label'])["+int_random+"]")).click();
             int_random = rand.nextInt(upperbound-1); 
            driver.get().findElement(By.xpath("(//span[@class='MuiIconButton-label'])[\"+int_random+\"]")).click();
            driver.get().findElement(By.linkText("Favourites")).click();	
            assertTrue(driver.get().findElements(By.xpath("//div[@class='shelf-stopper']")).size()>0);
    
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in page load");
        }
    }
    
 

}
