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
@RooToString
@RooJavaBean
@RooJson
public class Team {
    
    public static final String TEAM_TO_PLAYER = "MEMBER";
	public static final RelationshipType TEAM_TO_PLAYER_REL = DynamicRelationshipType.withName(TEAM_TO_PLAYER);

	public static final String TEAM_TO_DECK = "DECK";
	public static final RelationshipType TEAM_TO_DECK_REL = DynamicRelationshipType.withName(TEAM_TO_DECK);

	public static final String CURRENT_DECK = "CURRENT_DECK";
	public static final RelationshipType CURRENT_DECK_REL = DynamicRelationshipType.withName(TEAM_TO_DECK);

	@Indexed
    private String name;

    private String secret;

    @RelatedTo(elementClass = Deck.class, type = CURRENT_DECK, direction = OUTGOING )
    private Deck currentDeck;
    
    @RelatedTo(elementClass = Deck.class, type = TEAM_TO_DECK, direction = OUTGOING)
    private Set<Deck> decks;

    @RelatedTo(elementClass = Player.class, type = TEAM_TO_PLAYER, direction = OUTGOING)
    private Set<Player> members;
    
    public String toJson() {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(this);
    }
    
    public static String toJsonArray(Collection<Team> collection) {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(collection);
    }

	public void add(Deck deck) {
		decks.add(deck);
	}



}
