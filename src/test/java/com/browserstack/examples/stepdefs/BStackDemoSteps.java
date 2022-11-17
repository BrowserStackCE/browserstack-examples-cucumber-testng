package com.browserstack.examples.stepdefs;

import com.browserstack.examples.tests.RunWebDriverCucumberTests;
import com.browserstack.examples.utils.Utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.Keys;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class BStackDemoSteps {

    private WebDriver driver;
    private WebDriverWait wait; 

    @Before
    public void setUp() throws MalformedURLException {
        

        MutableCapabilities capabilities = new MutableCapabilities();
        HashMap<String, String> bstackOptions = new HashMap<>();
        bstackOptions.putIfAbsent("source", "cucumber-java:sample-sdk:v1.0");
        capabilities.setCapability("bstack:options", bstackOptions);
        driver = new RemoteWebDriver(new URL("https://hub.browserstack.com/wd/hub"), capabilities);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ######################## HOME ######################## //

    @And("I add two products to cart")
    public void iAddProductsToCart() {
         
        Utility.waitForJSLoad(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#\\31 > .shelf-item__buy-btn"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.float-cart__close-btn"))).click();
        driver.findElement(By.cssSelector("#\\32 > .shelf-item__buy-btn")).click();
        Utility.waitForJSLoad(driver);
    }

    @And("I click on Buy Button")
    public void iClickOnBuyButton() {
        driver.findElement(By.cssSelector(".buy-btn")).click();
    }

    @And("I press the Apple Vendor Filter")
    public void iPressTheAppleVendorFilter() {
        try {
            driver.findElement(By.cssSelector(".filters-available-size:nth-child(2) .checkmark")).click();
            Utility.waitForJSLoad(driver);
        }
        catch (NoSuchElementException e) {
            throw new AssertionError();
        }
    }

    @And("I order by lowest to highest")
    public void iOrderByLowestToHighest() {
        WebElement dropdown = driver.findElement(By.cssSelector("select"));
        dropdown.findElement(By.cssSelector("option[value = 'lowestprice']")).click();
    }

    @Then("I should see user {string} logged in")
    public void iShouldUserLoggedIn(String user) {
         
        Utility.waitForJSLoad(driver);
        try {
            String loggedInUser = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".username"))).getText();
            Assert.assertEquals(user, loggedInUser);
        } catch (NoSuchElementException e) {
            throw new AssertionError(user + " is not logged in");
        }
    }

    @Then("I should see no image loaded")
    public void iShouldSeeNoImageLoaded() {
         
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
        String src = "";
        try {
            src = driver.findElement(By.cssSelector("img[alt='iPhone 12']")).getAttribute("src");
            Assert.assertEquals(src.isEmpty(),false);
        } catch (NoSuchElementException e) {
            throw new AssertionError("No Images are loaded");
        }
    }

    @Then("I should see {int} items in the list")
    public void iShouldSeeItemsInTheList(int productCount) {
        try {
            Utility.waitForJSLoad(driver);
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".shelf-item__title"))));
            List<String> values = driver.findElements(By.cssSelector(".shelf-item__title")).stream().map(WebElement::getText).collect(Collectors.toList());
            Assert.assertEquals(productCount, values.size());
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in page load");
        }
    }

    @Then("I should see prices in ascending order")
    public void iShouldSeePricesInAscendingOrder() {
         
        try {
            Utility.waitForJSLoad(driver);
            // webDriverWait.until(ExpectedConditions.visibilityOf(ThreadLocalDriver.driver.findElement(By.cssSelector(".shelf-item__price > div.val > b"))));
            List<WebElement> priceWebElement = driver.findElements(By.cssSelector(".shelf-item__price > div.val > b"));
            Utility.waitForJSLoad(driver);
            Assert.assertTrue(Utility.isAscendingOrder(priceWebElement, priceWebElement.size()));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in page load");
        }
    }

    // ######################## LOG IN ######################## //

    @And("I press Log In Button")
    public void iPressLogin() {
        driver.findElement(By.cssSelector(".Button_root__24MxS")).click();
    }

    @Then("I should see {string} as Login Error Message")
    public void iShouldSeeAsLoginErrorMessage(String expectedMessage) {
         
        try {
            String errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".api-error"))).getText();
            Assert.assertEquals(expectedMessage, errorMessage);
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in logging in");
        }
    }

    @And("I SignIn as {string} with {string} password")
    public void iSignInAsWithPassword(String username, String password) {
         
        driver.findElement(By.linkText("Sign In")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#username > div > div:nth-child(1)"))).click();
        driver.findElement(By.id("react-select-2-input")).sendKeys(username);
        driver.findElement(By.id("react-select-2-input")).sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector("#password > div > div:nth-child(1)")).click();
        driver.findElement(By.id("react-select-3-input")).sendKeys(password);
        driver.findElement(By.id("react-select-3-input")).sendKeys(Keys.ENTER);
        driver.findElement(By.cssSelector(".Button_root__24MxS")).click();
        Utility.mockGPS(driver);
    }

    // ######################## COMMON ######################## //
    @Given("I navigate to website")
    public void iNavigateToWebsite() {
        if(System.getProperty("browserstack-local").equalsIgnoreCase("true")){
            driver.get("http://localhost:3000");
        }
        else{
            driver.get("https://bstackdemo.com");
        }
        
    }

    @And("I click on {string} link")
    public void iClickOnLink(String linkText) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText))).click();
    }

    @And("I type {string} in {string}")
    public void iTypeIn(String text, String inputName) {
         
        if (inputName.equalsIgnoreCase("username")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#username > div > div:nth-child(1)"))).click();
            driver.findElement(By.id("react-select-2-input")).sendKeys(text);
            driver.findElement(By.id("react-select-2-input")).sendKeys(Keys.ENTER);
        } else if (inputName.equalsIgnoreCase("password")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#password > div > div:nth-child(1)"))).click();
            driver.findElement(By.id("react-select-3-input")).sendKeys(text);
            driver.findElement(By.id("react-select-3-input")).sendKeys(Keys.ENTER);
        }
    }

    // ######################## CHECKOUT ######################## //

    @And("I type {string} in Post Code")
    public void iTypeInPostCode(String postCode) {
         driver.findElement(By.cssSelector(".dynamic-form-field--postCode #provinceInput")).sendKeys(postCode);
    }

    @And("I click on Checkout Button")
    public void iClickOnCheckoutButton() {
        driver.findElement(By.id("checkout-shipping-continue")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#checkout-app > div > div > div > div > a > button"))).click();
    }

    @And("I enter shipping details {string}, {string}, {string}, {string} and {string}")
    public void iEnterShippingDetailsAnd(String first, String last, String address, String province, String postCode) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstNameInput"))).sendKeys(first);
         driver.findElement(By.id("lastNameInput")).sendKeys(last);
         driver.findElement(By.id("addressLine1Input")).sendKeys(address);
         driver.findElement(By.id("provinceInput")).sendKeys(province);
         driver.findElement(By.id("postCodeInput")).sendKeys(postCode);
    }

    // ######################## Offer ######################## //

    @Then("I should see Offer elements")
	    public void iShouldSeeOfferElements() {
	        wait.until(ExpectedConditions.urlContains("offers"));
	        try {
	            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".p-6")));
	            Assert.assertNotNull(element);
	        } catch (NoSuchElementException e) {
	            throw new AssertionError("There are no offers in your region.");
	        }
	    }

    // ######################## Order ######################## //
    @Then("I should see elements in list")
    public void iShouldSeeElementsInList() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#logout")));
        WebElement element = null;
        try {
            element = driver.findElement(By.cssSelector("#__next > main > div > div"));
            List<WebElement> orders = element.findElements(By.tagName("div"));
            Assert.assertNotEquals(0, orders.size());
        } catch (NoSuchElementException e) {
            throw new AssertionError("There are no orders");
        }
    }

    @After
    public void teardown(Scenario scenario) throws Exception {
        Thread.sleep(2000);
        driver.quit();
    }

}