---
layout: post
title: Trivialt Model in Pure Neo4j
subtitle: Developer Transcript - Elaborative creation of a trivia graph
---

1. BDD driven to identify large pieces
2. then TDD to fill in specifics

Setup
-----
* start by writing [Cucumber](https://github.com/cucumber/cucumber) Features
* setup Maven project to write steps in Java, using [Cuke4Duke](https://github.com/cucumber/cuke4duke)

Writing Features
----------------

* basics: categorized Q&A, which we'll call Free-form Trivia

Modeling
--------

* create TrivialtWorld, the top-level application facade
* create FreeformTrivia, for trivia entries
* create Category, a simple named set of FreeformTrivia
* each Category will have a Node, stored in an Index
  * unfortunate duplication of text data, in Index and Node property. Refactor-Op
  * oh right, can't "get all" from an Index. How do I get all Category Nodes?
  * create a NodeMap to hold the set of Category nodes


