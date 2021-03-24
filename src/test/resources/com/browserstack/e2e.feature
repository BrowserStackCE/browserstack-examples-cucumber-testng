Feature: End to End Feature

	@E2E
  Scenario: End to End Scenario
    Given I navigate to website
		And I SignIn as "fav_user" with "testingisfun99" password
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
