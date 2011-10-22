package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.OUTGOING;

import java.util.Collection;
import java.util.Set;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

@NodeEntity
@RooJavaBean
@RooJson
public class FramedQuestion {

    public static final String FRAME_TO_QUESTION = "FRAMED_QUESTION";
	public static final RelationshipType FRAME_TO_QUESTION_REL = DynamicRelationshipType.withName(FRAME_TO_QUESTION);
	
    public static final String FRAME_TO_ANSWERS = "POSSIBLE_ANSWER";
	public static final RelationshipType FRAME_TO_ANSWERS_REL = DynamicRelationshipType.withName(FRAME_TO_ANSWERS);
	
	/**
	 * Whether the question is available during a live match.
	 */
	private Boolean available = false;
	
    @Indexed
    private String phrase;
    
    private String hint;
    
    private String explanation;

    @RelatedTo(elementClass = Question.class, type=FRAME_TO_QUESTION, direction = OUTGOING)
    private Question originalQuestion;

    @RelatedTo(elementClass = Answer.class, type=FRAME_TO_ANSWERS, direction = OUTGOING)
    private Set<Answer> possibleAnswers;
    
    public FramedQuestion() {;}

	public String toJson() {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").include("possibleAnswers").serialize(this);
    }
    
    public static String toJsonArray(Collection<FramedQuestion> collection) {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").include("possibleAnswers").serialize(collection);
    }

	public void add(Answer possibleAnswer) {
		possibleAnswers.add(possibleAnswer);		
	}

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Available: ").append(getAvailable()).append(", ");
        sb.append("Explanation: ").append(getExplanation()).append(", ");
        sb.append("Hint: ").append(getHint()).append(", ");
        sb.append("OriginalQuestion: ").append(getOriginalQuestion()).append(", ");
        sb.append("Phrase: ").append(getPhrase()).append(", ");
        sb.append("PossibleAnswers: ").append(getPossibleAnswers() == null ? "null" : getPossibleAnswers().size());
        return sb.toString();
    }

}
