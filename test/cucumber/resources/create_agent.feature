Feature: Create Agent
  As a player I want to crate agents for my virologist to use them to my advantage

  Scenario: Create agent successfully
    Given virologist learned some genome, enough aminoacid and nucleotide
    When virologist tries to create agent
    Then virologist receives an agent

  Scenario: Not enough aminoacid
    Given virologist learned some genome, not enough aminoacid
    When virologist tries to create some agent
    Then virologist receives no agent

  Scenario: Not enough nucleotide
    Given virologist learned some genome, not enough nucleotide
    When virologist tries to create new agent
    Then virologist receives no new agent