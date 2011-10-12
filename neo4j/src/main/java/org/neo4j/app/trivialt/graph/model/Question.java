package org.neo4j.app.trivialt.graph.model;

import org.neo4j.graphdb.*;

import javax.swing.text.GlyphView;

/**
 * A fully formed question, with punctuation.
 */
public class Question
{
    private static final String QUESTION_TEXT_PROP = "text";

    Node node;
    private static final RelationshipType CORRECT_ANSWER_REL = DynamicRelationshipType.withName( "answered_by" );
    private static final RelationshipType WRONG_ANSWER_REL = DynamicRelationshipType.withName( "misdirected_by" );

    public Question( Node node )
    {
        this.node = node;
    }

    public String getText()
    {
        return (String) node.getProperty( QUESTION_TEXT_PROP, "" );
    }

    public void setText( String text )
    {
        node.setProperty( QUESTION_TEXT_PROP, text );
    }

    public void setAnswer( Answer answer )
    {
        // remove existing answer, if any
        Relationship currentAnswerRel = node.getSingleRelationship( CORRECT_ANSWER_REL, Direction.OUTGOING );
        if (currentAnswerRel != null) {
            currentAnswerRel.delete();
        }
        node.createRelationshipTo( answer.node, CORRECT_ANSWER_REL );
    }

    public void setWrong( Answer answer )
    {
        node.createRelationshipTo( answer.node, WRONG_ANSWER_REL );
    }

    public Answer getAnswer()
    {
        Node answer = node.getSingleRelationship( CORRECT_ANSWER_REL, Direction.OUTGOING ).getEndNode();
        return new Answer(answer);
    }
}
