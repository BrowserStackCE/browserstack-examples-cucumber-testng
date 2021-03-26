Feature: Product Feature

  Scenario: Apply Apple Vendor Filter
    Given I navigate to website
    And I press the Apple Vendor Filter
    Then I should see 9 items in the list

  Scenario: Apply Lowest to Highest Order By
    Given I navigate to website
    And I order by lowest to highest
    Then I should see prices in ascending order