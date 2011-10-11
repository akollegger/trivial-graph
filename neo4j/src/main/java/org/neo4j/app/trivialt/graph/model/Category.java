package org.neo4j.app.trivialt.graph.model;

import org.neo4j.graphdb.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A Category is a named set of questions. Questions may appear in multiple categories
 */
public class Category
{
    public static final String NAME_PROP = "name";

    private Node node;
    private static final RelationshipType CATEGORIZE_REL = DynamicRelationshipType.withName( "categorizes" );

    public Category( Node node )
    {
        this.node = node;
    }

    public String getName()
    {
        return (String) node.getProperty( NAME_PROP, "" );
    }

    public void setName( String name )
    {
        node.setProperty( NAME_PROP, name );
    }

    public void include( Question question )
    {
        node.createRelationshipTo( question.node, CATEGORIZE_REL );
    }

    public Collection<Question> getQuestions()
    {
        Collection<Question> questions = new ArrayList<Question>();
        for ( Relationship rel : node.getRelationships( CATEGORIZE_REL, Direction.OUTGOING))
        {
            questions.add(new Question(rel.getEndNode()));
        }
        return questions;
    }
}
