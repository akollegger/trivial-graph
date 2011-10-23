---
layout: post
title: Trivialt Spring
subtitle: Developer Transcript - re-implementing using Spring Data Neo4j
---

The domain feels good. I know what I want. But, the pain of copy-pasting-correcting
boilerplate code drive me to an early switch to SDN. 

Create an SDN Project
---------------------

1. Totally cheat: clone 'Cineasts' for initial project setup.
2. Rename artifact, make sure it build and launches
3. Copy Cucumber features from neo4j-impl
4. Set up project for running Cucumber
5. Run integration-test. Runs, but nothing implemented. Perfect

* install egit to work in a sane configured management environment

(Re-)Develop
------------

* Re-implement steps, creating fresh implementation classes
* Stub classes, leaving empty methods
* Repository-style coding conflicts with domain-oriented API
  * persons.birth() -becomes-> persons.save(new Person())
* copy and modify `movies-test-context.xml` as `trivialt-test-context.xml` for setting up unit tests
* Problem: unique entities. Fields can be `\@Indexed` but that just make them searchable. 
  * I think this is surprising behavior for JPA-familiar devs
* GraphId annotation inexplicably makes things fail
* Indexed(fulltext=true, indexName="foo") 
  * fields using this annotation fail to findByPropertyVal(property,value)


Comforting STS about AspectJ
----------------------------
Don't worry STS, things are ok. 

* Import->Maven->Existing Maven Project
* Can't import, because the project dir is the worksapce dir. Bah.
* Create a peer directory for the STS workspace
* Remove STS filesystem artifacts from trivialt-spring directory
* Open the new workspace directory, re-try the import
* ok. 
* hm, seems to be happy. nice
* back to (Re-)Developing
* STS is *too fond* of the beachball.
