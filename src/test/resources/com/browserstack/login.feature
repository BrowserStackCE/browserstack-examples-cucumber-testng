
Feature: Login Feature


  Scenario Outline: Login as "<username>"
  	CSV Examples:data.csv
    Given I navigate to website
    And I click on "Sign In" link
    And I type "<username>" in "username"
    And I type "<password>" in "password"
    And I press Log In Button
    Then I should see user "<username>" logged in
    Then I click on Logout
    

      
   Scenario: Login as Locked User
    Given I navigate to website
    And I click on "Sign In" link
    And I type "locked_user" in "username"
    And I type "testingisfun99" in "password"
    And I press Log In Button
    Then I should see "Your account has been locked." as Login Error Message