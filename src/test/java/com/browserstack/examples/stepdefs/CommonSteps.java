package com.browserstack.examples.stepdefs;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

public class CommonSteps extends AbstractBaseSteps {

    @Given("I navigate to website")
    public void iNavigateToWebsite() {
        getWebDriver().get(getTestEndpoint());
    }

    @And("I click on {string} link")
    public void iClickOnLink(String linkText) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(linkText))).click();
    }

    @And("I type {string} in {string}")
    public void iTypeIn(String text, String inputName) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        if (inputName.equalsIgnoreCase("username")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#username > div > div:nth-child(1)"))).click();
            getWebDriver().findElement(By.id("react-select-2-input")).sendKeys(text);
            getWebDriver().findElement(By.id("react-select-2-input")).sendKeys(Keys.ENTER);
        } else if (inputName.equalsIgnoreCase("password")) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#password > div > div:nth-child(1)"))).click();
            getWebDriver().findElement(By.id("react-select-3-input")).sendKeys(text);
            getWebDriver().findElement(By.id("react-select-3-input")).sendKeys(Keys.ENTER);
        }
    }


}
