package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.INCOMING;

import java.util.Collection;
import java.util.Set;
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

    public static final String READY = "ready";

	@Indexed
    private String title;

    private Boolean featured = false;
    
    private String mode = READY;

    @RelatedTo(elementClass = Round.class, type = "ORDINAL", direction = INCOMING)
    private Set<Round> rounds;

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
