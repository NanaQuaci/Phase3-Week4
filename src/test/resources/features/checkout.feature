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
  Scenario: Successful Checkout
    When I enter "John" as First Name
    And I enter "Doe" as Last Name
    And I enter "12345" as Postal Code
    And I continue checkout
    And I finish checkout
    Then I should see the order confirmation message

  @negative @checkout
  Scenario: Missing First Name
    When I enter "" as First Name
    And I enter "Doe" as Last Name
    And I enter "12345" as Postal Code
    And I continue checkout
    Then I should see error "Error: First Name is required"

  @negative @checkout
  Scenario: Missing Last Name
    When I enter "John" as First Name
    And I enter "" as Last Name
    And I enter "12345" as Postal Code
    And I continue checkout
    Then I should see error "Error: Last Name is required"

  @negative @checkout
  Scenario: Missing Postal Code
    When I enter "John" as First Name
    And I enter "Doe" as Last Name
    And I enter "" as Postal Code
    And I continue checkout
    Then I should see error "Error: Postal Code is required"
