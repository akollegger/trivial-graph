package org.neo4j.app.trivialt.graph.model;

import org.neo4j.graphdb.Node;

/**
 * Trivia answer.
 */
public class Answer
{
    private static final String ANSWER_TEXT_PROP = "text";

    Node node;

    public Answer( Node node )
    {
        this.node = node;
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
