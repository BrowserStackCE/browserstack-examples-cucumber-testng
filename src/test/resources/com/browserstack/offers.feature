Feature: Offers Feature

  Scenario: Offers for mumbai geo-location
    Given I navigate to website
    And I click on "Sign In" link
    And I type "fav_user" in "username"
    And I type "testingisfun99" in "password"
    And I press Log In Button
    And I click on "Offers" link
    Then I should see Offer elements
