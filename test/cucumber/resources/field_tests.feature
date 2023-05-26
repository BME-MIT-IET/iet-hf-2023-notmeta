Feature: Testing fields
  Testing fields what they are containing

  Background:
    Given initialized fields

  Scenario Outline: Having the proper collectables on fields
    When having <Nucleotides> amount of nucleotides and <Amino> aminoacids
    And having <Axes> axes
    Then check the <Nucleotides>, <Amino> number of materials
    Then check the <Allequipment> number of equipments

    Examples:
      | Nucleotides | Amino | Axes | Allequipment |
      | 1           | 6     | 1    | 4            |
      | 2           | 5     | 2    | 5            |
      | 3           | 4     | 3    | 6            |
      | 4           | 3     | 4    | 7            |
      | 5           | 2     | 5    | 8            |
      | 6           | 1     | 6    | 9            |
      | 7           | 7     | 7    | 10           |
      | 8           | 8     | 8    | 11           |
      | 9           | 9     | 9    | 12           |