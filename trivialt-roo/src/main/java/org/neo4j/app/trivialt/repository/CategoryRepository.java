package org.neo4j.app.trivialt.repository;

import org.neo4j.app.trivialt.model.Category;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface CategoryRepository extends GraphRepository<Category> {

}
