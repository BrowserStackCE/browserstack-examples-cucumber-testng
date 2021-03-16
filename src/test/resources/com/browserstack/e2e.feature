Feature: End to End Feature

  Scenario: End to End Scenario
    Given I navigate to website
    And I click on "Sign In" link
    And I type "fav_user" in "username"
    And I type "testingisfun99" in "password"
    And I press Log In Button
    And I add two products to cart
    And I click on Buy Button
    And I type "first" in "firstNameInput" input
    And I type "last" in "lastNameInput" input
    And I type "test address" in "addressLine1Input" input
    And I type "test province" in "provinceInput" input
    And I type "123456" in "postCodeInput" input
    And I click on Checkout Button
    And I click on "Orders" link
    Then I should see elements in list
