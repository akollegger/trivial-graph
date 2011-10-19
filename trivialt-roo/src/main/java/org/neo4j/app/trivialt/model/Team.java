package org.neo4j.app.trivialt.model;

import java.util.Collection;
import java.util.Set;

import static org.springframework.data.neo4j.core.Direction.OUTGOING;

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

    @Indexed
    private String name;

    private String secret;

    @RelatedTo(elementClass = Deck.class, type = "OWNS", direction = OUTGOING)
    private Set<Deck> decks;

    @RelatedTo(elementClass = Player.class, type = "MEMBER", direction = OUTGOING)
    private Set<Player> members;
    
    public String toJson() {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(this);
    }
    
    public static String toJsonArray(Collection<Team> collection) {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(collection);
    }



}
