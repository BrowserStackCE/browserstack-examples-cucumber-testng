package com.browserstack.examples.stepdefs;


import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.And;


public class CheckoutPageSteps extends AbstractBaseSteps {

    @And("I type {string} in Post Code")
    public void iTypeInPostCode(String postCode) {
        getWebDriver().findElement(By.cssSelector(".dynamic-form-field--postCode #provinceInput")).sendKeys(postCode);
    }

    @And("I click on Checkout Button")
    public void iClickOnCheckoutButton() {
        getWebDriver().findElement(By.id("checkout-shipping-continue")).click();
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#checkout-app > div > div > div > div > a > button"))).click();
    }

    @And("I enter shipping details {string}, {string}, {string}, {string} and {string}")
    public void iEnterShippingDetailsAnd(String first, String last, String address, String province, String postCode) {
        WebDriverWait wait = new WebDriverWait(getWebDriver(), 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstNameInput"))).sendKeys(first);
        getWebDriver().findElement(By.id("lastNameInput")).sendKeys(last);
        getWebDriver().findElement(By.id("addressLine1Input")).sendKeys(address);
        getWebDriver().findElement(By.id("provinceInput")).sendKeys(province);
        getWebDriver().findElement(By.id("postCodeInput")).sendKeys(postCode);
    }

}
