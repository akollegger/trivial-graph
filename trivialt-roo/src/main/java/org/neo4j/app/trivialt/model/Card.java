package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.INCOMING;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.neo4j.app.trivialt.model.Round;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.neo4j.app.trivialt.model.Proposal;
import java.util.Set;

@NodeEntity
@RooToString
@RooJavaBean
public class Card {

    private Boolean locked;

    @RelatedTo
    private Round round;

    @RelatedTo(elementClass=Proposal.class, direction=INCOMING)
    private Set<Proposal> proposals;
}
