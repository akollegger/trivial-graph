package org.neo4j.app.trivialt.model;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

/**
 * A round of Match play, during which Players answer a series
 * of framed trivia questions.
 * 
 */
@NodeEntity
public class Round {

    @Indexed
    private String title;
   
    private Integer pointsPerQuestion;
    
    private List<FramedQuestion> framedQuestions;
        
    Round() {
    }

    Round(String title) {
    	setTitle(title);
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPointsPerQuestion() {
		return pointsPerQuestion;
	}

	public void setPointsPerQuestion(Integer pointsPerQuestion) {
		this.pointsPerQuestion = pointsPerQuestion;
	}

}
