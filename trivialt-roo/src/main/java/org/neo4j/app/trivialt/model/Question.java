package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.OUTGOING;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@NodeEntity
@RooToString
@RooJavaBean
public class Question {

	@Indexed
    private String text;

    @RelatedTo
    private Answer answer;

    @RelatedTo(elementClass=Category.class, type="CATEGORY", direction=OUTGOING)
    private Category category;
}
