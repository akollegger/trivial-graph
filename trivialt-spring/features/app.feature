@app
Feature: Trivialt Graph Application
  Scenario: App runs
    When graph-app is run
    Then it should succeed

  Scenario: App creates a Trivialt Graph Database
    When graph-app is run with "--graph test.graphdb"
    Then "test.graphdb" contains Neo4j graph files

  Scenario: App refuses to run against a non-trivialt graph
    Given a small neo4j database in directory "nontrivial.graphdb" with at least 2 nodes
    When graph-app is run with "--graph nontrivial.graphdb"
    Then it should complain that "is not a trivialt graph database"

  Scenario: App uses a pre-existing graph
    Given a trivialt graph database in directory "trivialt.graphdb"
    When graph-app is run with "--graph trivialt.graphdb"
    Then it should succeed
