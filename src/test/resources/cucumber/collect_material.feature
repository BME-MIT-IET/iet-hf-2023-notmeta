Feature: Collect material
  As a player I can take material from a warehouse

  Background:
    Given a virologist on a normal field with a neighboring warehouse

  Scenario Outline: Collect material successfully
    When virologist moves to the neighboring warehouse with <Quantity> amount of <MaterialType>
    And virologist tries to collect <Quantity> <MaterialType>
    Then the virologist should have <Quantity> amount of <MaterialType>
    And the warehouse will have 0 amount of <MaterialType>

    Examples:
      | Quantity    | MaterialType |
      | 3           | AminoAcid    |
      | 5           | Nucleotide   |