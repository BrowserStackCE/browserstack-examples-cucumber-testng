Feature: User Feature
	@ItemImage
  Scenario: Login as User with no image loaded
    Given I navigate to website
    And I click on "Sign In" link
   	And I SignIn as "image_not_loading_user" with "testingisfun99" password
    Then I should see no image loaded
    Then I click on Logout

@Orders
  Scenario: Login as User with existing Orders
    Given I navigate to website
		And I SignIn as "image_not_loading_user" with "testingisfun99" password
    And I click on "Orders" link
    Then I should see elements in list
    Then I click on Logout
@Favourites
 Scenario: Login as User with existing Orders & go to favourites
    Given I navigate to website
    And I SignIn as "image_not_loading_user" with "testingisfun99" password
   	And I should be able to add items to favourites
    Then I click on Logout