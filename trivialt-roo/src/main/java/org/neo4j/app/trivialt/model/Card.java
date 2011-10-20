package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.INCOMING;
import static org.springframework.data.neo4j.core.Direction.OUTGOING;

import java.util.Collection;
import java.util.Set;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

import flexjson.JSONSerializer;

@NodeEntity
@RooToString
@RooJavaBean
@RooJson
public class Card {

    public static final String CARD_TO_ROUND = "FOR_ROUND";
	public static final RelationshipType CARD_TO_ROUND_REL = DynamicRelationshipType.withName(CARD_TO_ROUND);

    public static final String CARD_TO_PROPOSALS = "PROPOSAL";
	public static final RelationshipType CARD_TO_PROPOSAL_REL = DynamicRelationshipType.withName(CARD_TO_PROPOSALS);
	
    private Boolean locked;

    @RelatedTo(elementClass = Round.class, type=CARD_TO_ROUND, direction = OUTGOING)
    private Round round;

    @RelatedTo(elementClass = Deck.class, type=Deck.DECK_TO_CARDS, direction = INCOMING)
    private Deck deck;

    @RelatedTo(elementClass = Proposal.class, type=CARD_TO_PROPOSALS, direction = OUTGOING)
    private Set<Proposal> proposals;
    
//    private String reference;
    
    public Card() {
	}

	public String toJson() {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(this);
    }
    
    public static String toJsonArray(Collection<Card> collection) {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(collection);
    }


}
