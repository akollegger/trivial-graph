package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.OUTGOING;

import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import java.util.Collection;
import java.util.Set;
import org.neo4j.app.trivialt.model.FramedQuestion;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.roo.addon.json.RooJson;

import flexjson.JSONSerializer;

/**
 * A Round of FramedQuestions during a Match.
 * 
 * When starting a round:
 * <ol>
 *   <li>Wait for the round to become available</li>
 *   <li>Get current question</li>
 *   <li>Wait for the question to become available</li>
 *   <li>Proposals can only be submitted while the round is available</li>
 *   <li>The round closes by becoming un-available again</li>
 * </ol>
 * 
 * @see Match
 * @see FramedQuestion
 */
@NodeEntity
@RooToString
@RooJavaBean
@RooJson
public class Round {

    public static final String ROUND_TO_FRAMES = "FRAME";
	public static final RelationshipType ROUND_TO_FRAMES_REL = DynamicRelationshipType.withName(ROUND_TO_FRAMES);

    public static final String ROUND_TO_CURRENT_FRAME = "CURRENT_FRAME";
	public static final RelationshipType ROUND_TO_CURRENT_FRAME_REL = DynamicRelationshipType.withName(ROUND_TO_CURRENT_FRAME);
	
    private String title;
    
    /**
     * Whether a round is available for play during a live match.
     */
    private Boolean available = false;
    
    Integer pointsPerQuestion = 0;

    @RelatedTo(elementClass=FramedQuestion.class, type=ROUND_TO_FRAMES, direction=OUTGOING)
    private Set<FramedQuestion> framedQuestions;

    @RelatedTo(elementClass=FramedQuestion.class, type=ROUND_TO_CURRENT_FRAME, direction=OUTGOING)
    private FramedQuestion currentQuestion;
    
    public Round() {;}
    
    public Round(String title) {
    	this.title = title;
	}

	public Ordinal getOrder(FramedQuestion ofQuestion) {
//        return getRelationshipTo(ofQuestion, Ordinal.class, "ORDINAL");
		return null;
    }
    
    public String toJson() {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(this);
    }
    
    public static String toJsonArray(Collection<Round> collection) {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(collection);
    }

	public void add(FramedQuestion frame) {
		framedQuestions.add(frame);
	}


}
