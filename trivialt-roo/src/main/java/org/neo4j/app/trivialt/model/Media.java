package org.neo4j.app.trivialt.model;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@NodeEntity
@RooToString
@RooJavaBean
public class Media {

	@Indexed
    private String url;

    private String contentType;

    @RelatedTo
    private FramedQuestion representedQuestion;
}
