Feature: Basic Validation
@Smoke @Regression
  Scenario: Google title verification
    When the user navigates to google
    And the user searches for "apple"
    Then verify "apple" is in the title of the page