---
layout: post
title: Trivialt Spring, Continued
subtitle: Developer Transcript - re-implementing using Spring Data Neo4j
---

Picking up with the conversion into an SDN project. The challenge switches from 
writing boilerplate code to divining the appropriate annotations and meeting the
expectations of the AspectJ processing.

(Re-)Develop
------------

* Added a Role to identify the founder of a Team
  * using Role.title for a named role, rather than a boolean isFounder
* Perplexed. Code compiles and runs, but I get nulls back attached to the Role
  * not adding the Role correctly, or reading it, or is the class wrong
  * aha, adding accessors and a no-arg constructor induced a thrown exception
    which hinted at a problem with the class. oh, duh.
  * blind copy-and-paste from the docs resulted in a class without explicit scope
    * must be public. make it so. success
* `List<Entity>` not supported? No ordered collection, just Sets. 
  * use the Set, but RelatedToVia with an Ordinal relationship
  * sort the collection after retrieval, using Ordinal.position


