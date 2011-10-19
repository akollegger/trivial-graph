package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.INCOMING;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.neo4j.app.trivialt.model.Match;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Collection;
import java.util.Set;
import org.neo4j.app.trivialt.model.Card;
import org.springframework.roo.addon.json.RooJson;

import flexjson.JSONSerializer;

@NodeEntity
@RooToString
@RooJavaBean
@RooJson
public class Deck {

    @RelatedTo(elementClass = Match.class)
    private Match match;

    @RelatedTo(elementClass = Card.class, direction = INCOMING)
    private Set<Card> cards;

    @RelatedTo(elementClass = Team.class, type = "OWNS", direction = INCOMING)
    private Team owner;
    
    public String toJson() {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(this);
    }
    
    public static String toJsonArray(Collection<Deck> collection) {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(collection);
    }


}
