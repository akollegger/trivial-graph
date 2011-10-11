package org.neo4j.app.trivialt.graph.model;

import org.neo4j.graphdb.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A Frame phrases a question and proposes possible Answers.
 */
public class Frame
{
    Node node;
    private static final RelationshipType QUESTION_REL = DynamicRelationshipType.withName( "phrases_question" );
    private static final RelationshipType ANSWER_REL = DynamicRelationshipType.withName( "proposes_answer" );

    public Frame( Node node )
    {
        this.node = node;
    }

    public void phrase( Question question )
    {
        node.createRelationshipTo( question.node, QUESTION_REL );
    }

    public Question getPhrase()
    {
        Relationship qrel = node.getSingleRelationship( QUESTION_REL, Direction.OUTGOING );
        if ( qrel != null )
        {
            return new Question( qrel.getEndNode() );
        }
        return null;
    }

    public void propose( Answer answer )
    {
        node.createRelationshipTo( answer.node, ANSWER_REL );
    }

    public Collection<Answer> getProposals()
    {
        Collection<Answer> proposals = new ArrayList<Answer>();
        for ( Relationship rel : node.getRelationships( ANSWER_REL, Direction.OUTGOING ) )
        {
            proposals.add( new Answer( rel.getEndNode() ) );
        }
        return proposals;
    }
}
