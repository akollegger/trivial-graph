package org.neo4j.app.trivialt.repository;

import org.neo4j.app.trivialt.model.Answer;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface Answers extends GraphRepository<Answer> {

}
