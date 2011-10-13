package org.neo4j.app.trivialt.model;

import java.util.Set;

import static org.neo4j.graphdb.Direction.INCOMING;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Category {
//	@GraphId long id;
	
	@Indexed
	String name;

    @RelatedTo(elementClass = Question.class, type = "CATEGORY", direction = INCOMING)
	Set<Question> questions;
    
    public Category() {;}
    
	public Category(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void include(Question q) {
		questions.add(q);
	}
	
	public Iterable<Question> getQuestion()
	{
		return questions;
	}
}
