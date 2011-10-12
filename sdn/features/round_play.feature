Feature: Playing one round of a match

  Background: A Trivialt match is live
    Given a trivialt graph database in directory "match.graphdb"
    And these freeform questions:
      | QUESTION                                                                 | ANSWER                             | CATEGORY            |
      | What did Gwyneth Paltrow and Chris Martin call their baby daughter?      | Apple (Apple Blythe Alison Martin) | Film and Television |
      | Port of Spain is the capital of which nation?                            | Trinidad and Tobago                | Geography           |
      | Which country was invaded by the Japanese in 1937?                       | China                              | History and Legend  |
      | Where did Roger Bannister run the first sub-four-minute mile in 1954?    | Oxford                             | Sports              |
      | What musical instrument comes from the Hawaiian word for 'jumping flea'? | Ukulele                            | Music               |
      | What's the answer to Life, the Universe, and Everything?                 | 42                                 | Literature          |
    And a "live" match called "Random Facts"
    And a round in "Random Facts" called "Easy" with these framed questions:
      | N | QUESTION                                                                                | PHRASED AS                                     | OFFERS ANSWERS                       |
      | 1 | Port of Spain is the capital of which nation?                                           | Port of Spain is the capital of which nation?  | Trinidad and Tobago, Spain, Portugal |
      | 2 | What musical instrument comes form the Hawaiian word for 'jumping flea'?                | Name a Hawaiian stringed intsrument.           | Ukulele                              |
      | 3 | What's the Ultimate answer to the Ultimate Question Life, the Universe, and Everything? | According to Arthur Dent, what is six by nine? | 42, 54, 69                           |
    And these players:
      | PLAYER HANDLE      | PLAYER NAME       |
      | @akollegger        | Andreas Kollegger |
      | @andres_taylor     | Andres Taylor     |
      | @micha             | Michael Hunger    |
      | @tbaum             | Thomas Baum       |
    And these teams:
      | TEAM NAME  | SECRET    | PLAYERS                     |
      | A Team     | neo4j     | @akollegger, @andres_taylor |
      | German Div | endurance | @micha, @tbaum              |
    And "A Team" has been registered with "Random Facts"

  Scenario: Join a match
    When "@akollegger" joins the match "Random Facts"
    Then "@akollegger" is currently playing "Random Facts"

  Scenario: Player answers a question incorrectly
    Given "@akollegger" is currently playing "Random Facts"
    And "Easy" is the current round of "Random Facts"
    And "Easy" is at round 1, question 1
    When "@akollegger" submits the answer "Portugal"
    Then the "A Team" playing card for "Abstract Facts" has a submitted proposal for round 1, question 1
    And the "A Team" playing card score is 0

  Scenario: Player answers a question correctly
    Given "@akollegger" is currently playing "Random Facts"
    And "Easy" is the current round of "Random Facts"
    And "Easy" is at round 1, question 2
    When "@akollegger" submits the answer "Ukulele"
    And the "A Team" playing card score is 1


