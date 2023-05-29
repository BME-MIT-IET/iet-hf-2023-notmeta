Feature: Collect equipment
  As a player I can pick up equipment from the field where I'm standing if it has equipment and I already moved

  Background:
    Given a virologist on a normal field who has already moved

  Scenario Outline: Pick up equipment successfully
    When normal field has one <EquipmentType>
    And virologist tries to collect the first equipment it finds on the ground
    Then the virologist should have one of <EquipmentType>
    And the field will have zero of <EquipmentType>

    Examples:
      | EquipmentType |
      | Axe           |
      | Cloak         |
      | Gloves        |
      | Sack          |
