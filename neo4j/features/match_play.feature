Feature: Playing a Trivialt match

  Background: Trivialt is ready to play
    Given a trivialt graph database in directory "match.graphdb"
    And these players:
      | player handle      | player name       |
      | @akollegger        | Andreas Kollegger |
      | @andres_taylor     | Andres Taylor     |
      | @micha             | Michael Hunger    |
      | @tbaum             | Thomas Baum       |
    And these teams:
      | team name  | secret    | players                     |
      | A Team     | neo4j     | @akollegger, @andres_taylor |
      | German Div | endurance | @micha, @tbaum              |

  Scenario: Create a match
    Given "@akollegger" is the current player
    When the current player create a match called "Abstract Facts"
    Then the match "Abstract Facts" is in "preparation" mode
    And the match "Abstract Facts" has 1 round
    And "@akollegger" is the Trivia Master of "Abstract Facts"

  Scenario: Add a round to a match
    Given "@akollegger" is the current player
    And the current player is the Trivia Master of "Abstract Facts"
    When the current player adds a round to the match "Abstract Facts"
    Then the match "Abstract Facts" has 2 rounds

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
    And "German Div" playing card for "Abstract Facts" has an unspecified proposal for the current question

