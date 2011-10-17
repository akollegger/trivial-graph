package org.neo4j.app.trivialt.model;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

/**
 * A FramedQuestion phrases a Question in a particular way, and offers possible
 * Answers, any or none of which may be correct.
 * 
 */
@NodeEntity
public class FramedQuestion {
	
	@Indexed
	private String phrase;
    
	private Question originalQuestion;
	
	@RelatedTo(elementClass=Answer.class, type="OFFERS", direction = OUTGOING)
	private Set<Answer> possibleAnswers;
	
	private FramedQuestion() {;}

	public FramedQuestion(Question originalQuestion) {
		this.setOriginalQuestion(originalQuestion);
	}

	public Question getOriginalQuestion() {
		return originalQuestion;
	}

	public void setOriginalQuestion(Question originalQuestion) {
		this.originalQuestion = originalQuestion;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public Iterable<Answer> getPossibleAnswers() {
		return possibleAnswers;
	}

	public void addPossibleAnswer(Answer answer)
	{
		this.possibleAnswers.add(answer);
	}

	
}
