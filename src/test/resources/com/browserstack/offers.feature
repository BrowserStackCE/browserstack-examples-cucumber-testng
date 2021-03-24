Feature: Offers Feature
	@Offers
  Scenario: Offers for mumbai geo-location
    Given I navigate to website
 	 	And I SignIn as "fav_user" with "testingisfun99" password
    And I click on "Offers" link
    Then I should see Offer elements
