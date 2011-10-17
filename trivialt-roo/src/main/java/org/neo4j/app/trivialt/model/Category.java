package org.neo4j.app.trivialt.model;

import static org.springframework.data.neo4j.core.Direction.INCOMING;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@NodeEntity
@RooToString
@RooJavaBean
public class Category {

	@Indexed
    private String name;

	@RelatedTo(elementClass = Question.class, type="CATEGORY", direction=INCOMING)
    private Set<Question> questions;
}
