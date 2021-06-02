Feature: Login Feature

  Scenario Outline: Login with given <username> user
    Given I navigate to website
    And I click on "Sign In" link
    And I type <username> in "username"
    And I type <password> in "password"
    And I press Log In Button
    Then I should see user <username> logged in
    Examples:
      | username                 | password         |
      | 'fav_user'               | 'testingisfun99' |
      | 'image_not_loading_user' | 'testingisfun99' |
      | 'existing_orders_user'   | 'testingisfun99' |
      
      
  Scenario: Login as Locked User
    Given I navigate to website
    And I click on "Sign In" link
    And I type "locked_user" in "username"
    And I type "testingisfun99" in "password"
    And I press Log In Button
    Then I should see "Your account has been locked." as Login Error Message