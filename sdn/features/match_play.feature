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
      | @akollegger | Ska Fans   | neo4j     | @akollegger, @systay |
      | @micha      | German Div | endurance | @micha, @tbaum              |
    And these matches:
      | MASTER      | MATCH TITLE      | TEAMS    |
      | @akollegger | Thirsty Thursday | Ska Fans |
      
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
    And "Thirsty Thursday" is the current match
    When the current player opens the current match for registration
    Then the current match is in "registration" mode

  Scenario: Register team for a match
    Given "German Div" is the current team
    And "Thirsty Thursday" is in "registration" mode
    When the current team joins the match "Thirsty Thursday"
    Then "Thirsty Thursday" should be the current match
    And "German Div" has a deck for "Thirsty Thursday"
