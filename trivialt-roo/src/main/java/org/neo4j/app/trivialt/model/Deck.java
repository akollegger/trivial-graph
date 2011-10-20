package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.INCOMING;
import static org.springframework.data.neo4j.core.Direction.OUTGOING;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.neo4j.app.trivialt.model.Match;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Collection;
import java.util.Set;
import org.neo4j.app.trivialt.model.Card;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.roo.addon.json.RooJson;

import flexjson.JSONSerializer;

@NodeEntity
@RooToString
@RooJavaBean
@RooJson
public class Deck {
    public static final String DECK_TO_MATCH = "MEMBER";
	public static final RelationshipType DECK_TO_MATCH_REL = DynamicRelationshipType.withName(DECK_TO_MATCH);
	
    public static final String DECK_TO_CARDS = "CARD";
	public static final RelationshipType DECK_TO_CARDS_REL = DynamicRelationshipType.withName(DECK_TO_CARDS);

	@RelatedTo(elementClass = Match.class, type=DECK_TO_MATCH)
    private Match match;

    @RelatedTo(elementClass = Card.class, type=DECK_TO_CARDS, direction = OUTGOING)
    private Set<Card> cards;

    @RelatedTo(elementClass = Team.class, type = Team.TEAM_TO_DECK, direction = INCOMING)
    private Team team;
    
    public Deck() { ; }

	public String toJson() {
        return new JSONSerializer().rootName("deck").exclude("*.class", "*.persistentState", "*.entityState").serialize(this);
    }
    
    public static String toJsonArray(Collection<Deck> collection) {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(collection);
    }


}
