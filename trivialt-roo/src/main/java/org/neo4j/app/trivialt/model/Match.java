package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.INCOMING;
import static org.springframework.data.neo4j.core.Direction.OUTGOING;

import java.util.Collection;
import java.util.Set;

import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.json.RooJson;

import flexjson.JSONSerializer;

@NodeEntity
@RooToString
@RooJavaBean
@RooJson
public class Match {

    public static final String MATCH_TO_ROUNDS = "ROUND";
	public static final RelationshipType MATCH_TO_ROUNDS_REL = DynamicRelationshipType.withName(MATCH_TO_ROUNDS);
	
	@Indexed
    private String title;
	
	private String introduction;

    private Boolean featured = false;
    
    private Boolean available = false;

    @RelatedTo(elementClass = Round.class, type = MATCH_TO_ROUNDS, direction = OUTGOING)
    private Set<Round> rounds;
    
    @RelatedTo(elementClass = Deck.class, type = Deck.DECK_TO_MATCH, direction = INCOMING)
    private Set<Deck> decks;

    @RelatedTo(elementClass = Player.class)
    private Player triviaMaster;

    @RelatedTo(elementClass = Round.class)
    private Round currentRound;
    
    public Match() {;}

    public Match(String title) {
    	this.title = title;
	}

	public Ordinal getOrder(Round ofRound) {
        return getRelationshipTo(ofRound, Ordinal.class, "ORDINAL");
    }

    public String toJson() {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(this);
    }
    
    public static String toJsonArray(Collection<Match> collection) {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(collection);
    }

	public void add(Round round) {
		rounds.add(round);
	}


}
