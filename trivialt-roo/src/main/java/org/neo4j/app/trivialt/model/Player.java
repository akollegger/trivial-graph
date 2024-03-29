package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.INCOMING;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.springframework.roo.addon.json.RooJson;

import flexjson.JSONSerializer;

@NodeEntity
@RooToString
@RooJavaBean
@RooJson
public class Player {

    @Indexed
    private String handle;

    @Indexed(fulltext = true, indexName = "people")
    private String name;

    @RelatedTo(elementClass = Player.class)
    private Set<org.neo4j.app.trivialt.model.Player> friends;

    @RelatedTo(elementClass = Team.class, type = "MEMBER", direction = INCOMING)
    private Set<Team> teams;

    public Role getRole(Team onTeam) {
        return getRelationshipTo(onTeam, Role.class, "MEMBER");    
    }
    
    public String toJson() {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(this);
    }
    
    public static String toJsonArray(Collection<Player> collection) {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(collection);
    }


}
