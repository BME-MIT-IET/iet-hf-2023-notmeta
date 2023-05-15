Feature: Move Virologist
  As a player I want to move around the map with my virologist

  Scenario: Move to a normal field
    Given virologist is on a field and it is his turn
    When virologist uses the move action
    Then virologist moves to the next available normal field

  Scenario: Try to move when unable
    Given virologist is on a field and is effected by paralysis
    When virologist tries to use the move action
    Then virologist does not move away

  Scenario: Move randomly
    Given virologist is on a field and is effected by chorea
    When virologist uses move
    Then virologist moves randomly