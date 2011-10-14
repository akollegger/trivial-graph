@match
Feature: Playing a Trivialt match

  Background: Trivialt is ready to play
    Given these players:
      | PLAYER HANDLE      | PLAYER NAME       |
      | @akollegger        | Andreas Kollegger |
      | @systay            | Andres Taylor     |
      | @micha             | Michael Hunger    |
      | @tbaum             | Thomas Baum       |
    And these teams:
      | FOUNDER     | TEAM NAME  | SECRET    | PLAYERS                     |
      | @akollegger | A Team     | neo4j     | @akollegger, @systay |
      | @micha      | German Div | endurance | @micha, @tbaum              |
    And these matches:
      | MASTER      | MATCH TITLE      |
      | @akollegger | Thirsty Thursday |
      
  Scenario: Create a match
    Given "@akollegger" is the current player
    When the current player creates a match called "Abstract Facts"
    Then "Abstract Facts" should be the current match
    And the current match is in "unprepared" mode
    And the current match has 1 round
    And "@akollegger" is the Trivia Master of "Abstract Facts"
    
  Scenario: Fail to create a match with conflicting name
  	Given "@systay" is the current player
    When the current player creates a duplicate match called "Thirsty Thursday"
    Then "@systay" is not the Trivia Master of "Thirsty Thursday"

  Scenario: Add a round to a match
    Given "@akollegger" is the current player
    And "Thirsty Thursday" is the current match
    When the current player adds a round to the match
    Then the current match has 2 rounds

  Scenario: Open match for registration
    Given "@akollegger" is the current player
    And the current player is the Trivia Master of "Abstract Facts"
    When the current player opens "Abstract Facts" for registration
    Then the match "Abstract Facts" is in "registration" mode

  Scenario: Register team for a match
    Given "@micha" is the current player
    And "Abstract Facts" is in "registration" mode
    When the current player enters "German Div" to the match "Abstract Facts"
    Then "German Div" receives a playing card for "Abstract Facts"

  Scenario: Begin match play
    Given "@akollegger" is the current player
    And the current player is the Trivia Master of "Abstract Facts"
    And team "German Div" has a playing card for "Abstract Facts"
    When the current player starts the match "Abstract Facts"
    Then the match "Abstract Facts" is in "live" mode
    And the current round of "Abstract Facts" is round 1
    And the current question of "Abstract Facts" is question 1
    And the "German Div" playing card for "Abstract Facts" has an unspecified proposal for round 1, queston 1

