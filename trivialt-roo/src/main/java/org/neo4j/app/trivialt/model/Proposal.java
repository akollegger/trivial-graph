package org.neo4j.app.trivialt.model;

import java.util.Collection;

import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.neo4j.app.trivialt.model.Card;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.neo4j.app.trivialt.model.FramedQuestion;
import org.neo4j.app.trivialt.model.Answer;
import org.neo4j.app.trivialt.model.Player;
import org.springframework.roo.addon.json.RooJson;

import flexjson.JSONSerializer;

@NodeEntity
@RooToString
@RooJavaBean
@RooJson
public class Proposal {

    @RelatedTo
    private Card card;

    @RelatedTo
    private FramedQuestion posedQuestion;

    @RelatedTo
    private Answer proposedAnswer;

    @RelatedTo
    private Player submittingPlayer;
    
    public String toJson() {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(this);
    }
    
    public static String toJsonArray(Collection<Proposal> collection) {
        return new JSONSerializer().exclude("*.class", "*.persistentState", "*.entityState").serialize(collection);
    }


}
