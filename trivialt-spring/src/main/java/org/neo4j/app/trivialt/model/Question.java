package org.neo4j.app.trivialt.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class Question {
	//@GraphId long id;

    //@Indexed(fulltext = true, indexName = "questions")
	@Indexed
	String text;
    
	private Answer answer;
	
	private Question() {;}

	public Question(String text, Answer answer) {
		this.text = text;
		this.answer = answer;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
}
