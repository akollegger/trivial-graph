package org.neo4j.app.trivialt.repository;

import org.neo4j.app.trivialt.model.Player;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface PlayerRepository extends GraphRepository<Player> {
}
