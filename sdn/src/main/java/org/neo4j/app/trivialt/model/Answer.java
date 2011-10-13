package org.neo4j.app.trivialt.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Answer {
	
//	@GraphId long id;
	
	@Indexed
	private String text;
	
	public Answer()
	{
		; // no-arg for magic friendliness
	}

	public Answer(String text)
	{
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
