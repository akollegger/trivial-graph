package org.neo4j.app.trivialt.model;

import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Set;
import org.neo4j.app.trivialt.model.FramedQuestion;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.roo.addon.json.RooJson;

@NodeEntity
@RooToString
@RooJavaBean
@RooJson
public class Round {

    private String title;

    @RelatedTo
    private Set<FramedQuestion> framedQuestions;

    public Ordinal getOrder(FramedQuestion ofQuestion) {
        return getRelationshipTo(ofQuestion, Ordinal.class, "ORDINAL");
    }
}