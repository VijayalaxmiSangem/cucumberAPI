Feature: Go Rest Feature

  Background: Create a user
    Given creating a user

  @smoke
  Scenario: Verify that user resource can be updated
    When updating the user
    Then the user is updated

  @regression
  Scenario: Verify that user resource can be created
    Then the user is created

  @smoke
  Scenario Outline: Verify that a user resource not to be updated
    When updating the user with invalid input "<userName>" and "<password>"
    Then the user not to be updated
    Examples:
    | userName      | password|
    | test@gmail.com| test123 |
    | test          | Test123 |
    |gmail.com      |test123  |
    | demo@gmail.com|test@123 |


