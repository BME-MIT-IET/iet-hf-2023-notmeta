Feature: Kill a virologist
  As two virologists on one field with an axe, one kills the other

  Background:
    Given two virologists on one field containing an axe

  Scenario: Kill the other Virologist
    When the first virologist tries to kill the second virologist but has not picked up the axe
    Then no virologists should be dead
    And the first virologist picks up the axe
    Then attempts to kill again
    Then the second virologist should be dead