Feature: Hotel Users

  Scenario: new account creation

    Given an open browser with https://hotel-testlab.coderslab.pl/en/
    When new user register
    Then an account is created
    And close browser


  Scenario: functionality of first added address can be checked

    Given an open browser with https://hotel-testlab.coderslab.pl/en/
    When existing user logged into the account
    Then new address can be added to the account
    And close browser
