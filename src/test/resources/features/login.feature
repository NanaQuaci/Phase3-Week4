@login
Feature: Login Functionality

  Scenario Outline: Valid login
    Given I am on the login page
    When I login with username "<username>" and password "<password>"
    Then I should be redirected to the inventory page

    Examples:
      | username      | password       |
      | standard_user | secret_sauce   |

  @negative
  Scenario Outline: Invalid login shows an error
    Given I am on the login page
    When I login with username "<username>" and password "<password>"
    Then I should see an error message containing "epic sadface"

    Examples:
      | username        | password       |
      | standard_user   | wrong_password |
      | locked_user     | secret_sauce   |
      |                 |                |
      | standard_user   |                |
      |                 | secret_sauce   |
