Feature: End to End Feature

  Scenario: End to End Scenario
    Given I navigate to website
    And I SignIn as "fav_user" with "testingisfun99" password
    And I add two products to cart
    And I click on Buy Button
    And I enter shipping details "first", "last", "test address", "test province" and "123456"
    And I click on Checkout Button
    And I click on "Orders" link
    Then I should see elements in list
