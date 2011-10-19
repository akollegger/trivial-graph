package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.INCOMING;
import java.util.Set;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.addon.json.RooJson;

@NodeEntity
@RooToString
@RooJavaBean
@RooJson
public class Match {

    @Indexed
    private String title;

    private String mode;

    @RelatedTo(elementClass = Round.class, type = "ORDINAL", direction = INCOMING)
    private Set<Round> rounds;

    @RelatedTo(elementClass = Player.class)
    private Player triviaMaster;

    @RelatedTo(elementClass = Round.class)
    private Round currentRound;

    public Ordinal getOrder(Round ofRound) {
        return getRelationshipTo(ofRound, Ordinal.class, "ORDINAL");
    }
}
