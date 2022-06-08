@functional
  Feature: CRUD Checking Account for specific user

    ## Repetitive steps
    ##Given - precondition for our test
    ##When - action
    ##Then - validation

    ##Example table which with Scenario Outline (runs multiple times with different set of data)
    ##Data table that goes under a specific step (runs as 1 set of data)

    Background: get valid authentication bearer token
      Given the admin user is authenticated

    Scenario: I should be able to create new individual standard checking account with valid intro deposit
      When I send a following create account payload
        | accountName       | accountTypeCode | openingDeposit | ownerTYpeCode |
        | Lebron James Test | SCK             | 25             | IND           |
      Then response status code should be '200'
      And following should be the response payload
        | id  | name             | account number | currentBalance | openingBalance |
        | 152 | Askal Checking 2 | 478923733      | 25.00          | 25.00          |
      And the following should be the accountType payload
        | id | code | category | name              | mimiDeposit | OverdraftLimit | overdraftFee |
        | 8  | ScK  | CHK      | Standard Checking | 25          | 25             | 10           |
      And the following should be the ownershipTYpe payload
        | id | code | name       |
        | 17 | IND  | individual |
      And the following should be accountStanding payload
        | id | Code | name |
        | 19 | A1   | Open |

    Scenario: I should be able to update  account
        When I send a valid update account payload
        Then response status code should be '201'
        And create account response should be valid

      Scenario: I should be able to read account page
        Then response status code should be '200'
        And create account response should be valid

      Scenario: I should be able to delete account
        When I send a valid delete account payload
        Then response status code should be '202'
        And create account response should be valid