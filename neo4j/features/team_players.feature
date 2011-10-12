@players
Feature: Trivialt Players and Teams

  Background: Trivialt is ready to play
    Given these players:
      | PLAYER HANDLE      | PLAYER NAME       |
      | @akollegger        | Andreas Kollegger |
      | @andres_taylor     | Andres Taylor     |
      | @micha             | Michael Hunger    |
      | @twarko            | M. Rod            |
    And these teams:
      | FOUNDER     | TEAM NAME  | SECRET | PLAYERS                     |
      | @akollegger | ska fans   | scala  | @akollegger, @andres_taylor |

  Scenario: Register as a new player
    When you register "Tobias Ivarsson" as "@thobe"
    Then trivialt knows "@thobe" is "Tobias Ivarsson"
    And "@thobe" is the current player

  Scenario: Friend another player
    Given "@akollegger" is the current player
    When the current player friends "@andres_taylor"
    Then "@akollegger" is known to "@andres_taylor"

  Scenario: Start a team
    Given "@andres_taylor" is the current player
    When a new team called "graphistas" with secret "neo4j" is created
    Then "@andres_taylor" is a member of "graphistas"

  Scenario: Join an existing team
    Given "@micha" is the current player
    When the current player tries to join "ska fans" by whispering "scala"
    Then "@micha" is a member of "ska fans"

  Scenario: Denied access to team
    Given "@twarko" is the current player
    When the current player tries to join "ska fans" by whispering "groovy"
    Then "@twarko" is not a member of "ska fans"

