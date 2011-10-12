package org.neo4j.app.trivialt.graph.model;

import org.neo4j.graphdb.Node;

/**
 * Trivia answer.
 */
public class Answer extends EntityBase
{
    private static final String ANSWER_TEXT_PROP = "text";

    public Answer( Node node )
    {
        super( node );
    }

    public String getText()
    {
        return (String) node.getProperty( ANSWER_TEXT_PROP, "" );
    }

    public void setText( String text )
    {
        node.setProperty( ANSWER_TEXT_PROP, text );
    }

}
