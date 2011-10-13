package org.neo4j.app.trivialt.repository;

import org.neo4j.app.trivialt.model.Team;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface Teams extends GraphRepository<Team> {

}
