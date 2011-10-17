package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.OUTGOING;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@NodeEntity
@RooToString
@RooJavaBean
public class FramedQuestion {

	@Indexed
    private String phrase;

    @RelatedTo(elementClass=Question.class, direction=OUTGOING)
    private Question originalQuestion;
    
    @RelatedTo(elementClass=Answer.class, direction=OUTGOING)
    private Set<Answer> possibleAnswers;
    
}
