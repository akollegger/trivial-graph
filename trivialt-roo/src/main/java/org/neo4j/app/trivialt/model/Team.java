package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.OUTGOING;

import java.util.Set;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@NodeEntity
@RooToString
@RooJavaBean
public class Team {

	@Indexed
    private String name;

    private String secret;

    @RelatedTo(elementClass=Deck.class, type="OWNS", direction=OUTGOING)
    private Set<Deck> decks;
    
    @RelatedTo(elementClass=Player.class, type="MEMBER", direction=OUTGOING)
    private Set<Player> members;
    
}
