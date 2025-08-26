@cart
Feature: Cart Functionality

  Background:
    Given I am on the login page
    When I login with username "standard_user" and password "secret_sauce"
    Then I should be redirected to the inventory page

  Scenario: Add item to cart only
    When I add an item to the cart
    Then the cart badge should show "1"

  Scenario: Add and remove item from cart
    When I add an item to the cart
    Then the cart badge should show "1"
    When I open the cart
    Then I should see the item in the cart
    When I remove the item from the cart
    Then I should not see the item in the cart

  Scenario: Remove item without opening cart
    When I add an item to the cart
    Then the cart badge should show "1"
    When I remove the item from the products page
    Then the cart badge should not be visible
