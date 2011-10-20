package org.neo4j.app.trivialt.model;

import java.util.Collection;

import static org.springframework.data.neo4j.core.Direction.INCOMING;
import static org.springframework.data.neo4j.core.Direction.OUTGOING;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.neo4j.app.trivialt.model.Card;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.neo4j.app.trivialt.model.FramedQuestion;
import org.neo4j.app.trivialt.model.Answer;
import org.neo4j.app.trivialt.model.Player;
import org.springframework.roo.addon.json.RooJson;

import flexjson.JSONSerializer;

@NodeEntity
@RooToString
@RooJavaBean
@RooJson
public class Proposal {
	
	public static final String PROPOSAL_TO_FRAMED_QUESTION = "FRAMED_QUESTION";
	
	private Integer score = 0;
	
    private String proposedAnswer;
    
    @RelatedTo(elementClass=Card.class, type=Card.CARD_TO_PROPOSALS, direction=INCOMING)
    private Card card;

    @RelatedTo(elementClass=FramedQuestion.class, type=PROPOSAL_TO_FRAMED_QUESTION, direction=OUTGOING)
    private FramedQuestion posedQuestion;


    @RelatedTo
    private Player submittingPlayer;
    
    public String toJson() {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(this);
    }
    
    public static String toJsonArray(Collection<Proposal> collection) {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(collection);
    }


}
