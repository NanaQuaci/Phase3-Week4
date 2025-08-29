@checkout
Feature: Checkout Functionality
  As a Swag Labs customer
  I want to checkout items in my cart
  So that I can successfully place an order

  Background:
    Given I am logged in with valid credentials
    And I have added an item to the cart
    And I navigate directly to the checkout page

  @smoke @checkout
  Scenario Outline: Successful Checkout
    When I enter "<firstName>" as First Name
    And I enter "<lastName>" as Last Name
    And I enter "<postalCode>" as Postal Code
    And I continue checkout
    And I finish checkout
    Then I should see the order confirmation message

    Examples:
      | firstName | lastName | postalCode |
      | John      | Doe      | 12345      |

  @negative @checkout
  Scenario Outline: Checkout with missing required fields
    When I enter "<firstName>" as First Name
    And I enter "<lastName>" as Last Name
    And I enter "<postalCode>" as Postal Code
    And I continue checkout
    Then I should see error "<errorMessage>"

    Examples:
      | firstName | lastName | postalCode | errorMessage                     |
      |           | Doe      | 12345      | Error: First Name is required    |
      | John      |          | 12345      | Error: Last Name is required     |
      | John      | Doe      |            | Error: Postal Code is required   |
