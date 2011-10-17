package org.neo4j.app.trivialt.repository;

import org.neo4j.app.trivialt.model.Question;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface QuestionRepository extends GraphRepository<Question> {

}
