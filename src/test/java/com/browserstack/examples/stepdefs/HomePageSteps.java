package com.browserstack.examples.stepdefs;



import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.browserstack.examples.utils.Utility;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;


public class HomePageSteps extends AbstractBaseSteps {

    WebDriverWait wait;
    @And("I add two products to cart")
    public void iAddProductsToCart() {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 10);
        Utility.waitForJSLoad(getWebDriver());
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#\\31 > .shelf-item__buy-btn"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.float-cart__close-btn"))).click();
        getWebDriver().findElement(By.cssSelector("#\\32 > .shelf-item__buy-btn")).click();
        Utility.waitForJSLoad(getWebDriver());
    }

    @And("I click on Buy Button")
    public void iClickOnBuyButton() {
        getWebDriver().findElement(By.cssSelector(".buy-btn")).click();
    }

    @And("I press the Apple Vendor Filter")
    public void iPressTheAppleVendorFilter() {
        try {
            getWebDriver().findElement(By.cssSelector(".filters-available-size:nth-child(2) .checkmark")).click();
            Utility.waitForJSLoad(getWebDriver());
        }
        catch (NoSuchElementException e) {
            throw new AssertionError();
        }
    }

    @And("I order by lowest to highest")
    public void iOrderByLowestToHighest() {
        WebElement dropdown = getWebDriver().findElement(By.cssSelector("select"));
        dropdown.findElement(By.cssSelector("option[value = 'lowestprice']")).click();
    }

    @Then("I should see user {string} logged in")
    public void iShouldUserLoggedIn(String user) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 10);
        Utility.waitForJSLoad(getWebDriver());
        try {
            String loggedInUser = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".username"))).getText();
            Assert.assertEquals(user, loggedInUser);
        } catch (NoSuchElementException e) {
            throw new AssertionError(user + " is not logged in");
        }
    }

    @Then("I should see no image loaded")
    public void iShouldSeeNoImageLoaded() {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout")));
        String src = "";
        try {
            src = getWebDriver().findElement(By.cssSelector("img[alt='iPhone 12']")).getAttribute("src");
            Assert.assertEquals(src.isEmpty(),false);
        } catch (NoSuchElementException e) {
            throw new AssertionError("No Images are loaded");
        }
    }

    @Then("I should see {int} items in the list")
    public void iShouldSeeItemsInTheList(int productCount) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(getWebDriver(), 10);
            Utility.waitForJSLoad(getWebDriver());
            webDriverWait.until(ExpectedConditions.visibilityOf(getWebDriver().findElement(By.cssSelector(".shelf-item__title"))));
            List<String> values = getWebDriver().findElements(By.cssSelector(".shelf-item__title")).stream().map(WebElement::getText).collect(Collectors.toList());
            Assert.assertEquals(productCount, values.size());
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in page load");
        }
    }

    @Then("I should see prices in ascending order")
    public void iShouldSeePricesInAscendingOrder() {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 10);
        try {
            WebDriverWait webDriverWait = new WebDriverWait(getWebDriver(), 10);
            Utility.waitForJSLoad(getWebDriver());
            // webDriverWait.until(ExpectedConditions.visibilityOf(ThreadLocalDriver.getWebDriver().findElement(By.cssSelector(".shelf-item__price > div.val > b"))));
            List<WebElement> priceWebElement = getWebDriver().findElements(By.cssSelector(".shelf-item__price > div.val > b"));
            Utility.waitForJSLoad(getWebDriver());
            Assert.assertTrue(Utility.isAscendingOrder(priceWebElement, priceWebElement.size()));
        } catch (NoSuchElementException e) {
            throw new AssertionError("Error in page load");
        }
    }

}
