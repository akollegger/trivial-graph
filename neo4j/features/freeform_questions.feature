Feature: Free-form Questions

  Background: Known freeform questions
    Given a trivialt graph database in directory "freeform.graphdb"
    And these freeform questions:
      | question                                                                 | answer                             | category            |
      | What did Gwyneth Paltrow and Chris Martin call their baby daughter?      | Apple (Apple Blythe Alison Martin) | Film and Television |
      | Port of Spain is the capital of which nation?                            | Trinidad and Tobago                | Geography           |
      | Which country was invaded by the Japanese in 1937?                       | China                              | History and Legend  |
      | Where did Roger Bannister run the first sub-four-minute mile in 1954?    | Oxford                             | Sport               |
      | What musical instrument comes from the Hawaiian word for 'jumping flea'? | Ukulele                            | Music               |

  Scenario: Categorized questions
    When looking in the database
    Then the categories should include "Sport, Music, Geography"

  Scenario: Get a question for a category
    When asked for a question from the "Sport" category
    Then the question posed should be "Where did Roger Bannister run the first sub-four-minute mile in 1954?"

  Scenario: Answer a freeform question
    When asked the question "Port of Spain is the capital of which nation?"
    Then the answer should be "Trinidad and Tobago"


