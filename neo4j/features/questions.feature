Feature: Trivialt Questions

  Scenario: Freeform questions
    Given these freeform questions:
      | question                                                                 | answer                             | category            |
      | What did Gwyneth Paltrow and Chris Martin call their baby daughter?      | Apple (Apple Blythe Alison Martin) | Film and Television |
      | Port of Spain is the capital of which nation?                            | Trinidad and Tobago                | Geography           |
      | Which country was invaded by the Japanese in 1937?                       | China                              | History and Legend  |
      | Where did Roger Bannister run the first sub-four-minute mile in 1954?    | Oxford                             | Sport               |
      | What musical instrument comes from the Hawaiian word for 'jumping flea'? | Ukulele                            | Music               |
    When asked the question "Port of Spain is the capital of which nation?"
    Then the answer should be "Trinidad and Tobago"

  Scenario: Yes/No questions
    Given

  Scenario: Multiple-Choice questions

  Scenario: Quantitative questions

  Scenario: Interrogative fact-based questions
    Given these facts:
      | subject    | predicate         | object            |
      | akollegger | was born in year  | 1969              |
      | akollegger | works for         | Neo Technology    |
      | akollegger | has full name     | Andreas Kollegger |
    When asked the question "When was Andreas Kollegger born?"
    Then the answer should be "1969"

