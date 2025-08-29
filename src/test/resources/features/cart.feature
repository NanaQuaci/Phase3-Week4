@cart
Feature: Cart Functionality

  Background:
    Given I am logged in with valid credentials

  Scenario: Add item to cart only
    When I add an item to the cart
    Then the item should be added to the cart

  Scenario: Add and remove item from cart
    When I add an item to the cart
    Then the item should be added to the cart
    When I open the cart
    Then I should see the item in the cart
    When I remove the item from the cart
    Then I should not see the item in the cart

  Scenario: Remove item without opening cart
    When I add an item to the cart
    Then the item should be added to the cart
    When I remove the item from the products page
    Then I should not see the item in the cart
