package org.neo4j.app.trivialt.graph.model;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;

/**
 * A Proposal proposes an Answer to a framed Question.
 */
public class Proposal
{
    Node node;
    private static final RelationshipType PROPOSED_ANSWER_REL = DynamicRelationshipType.withName( "proposed_answer" );
    private static final RelationshipType IN_RESPONSE_TO = DynamicRelationshipType.withName( "in_response_to" );

    public Proposal( Node node )
    {
        this.node = node;
    }

    public void setFramedQuestion( Frame framedQuestion )
    {
        node.createRelationshipTo( framedQuestion.node, IN_RESPONSE_TO );
    }

    public Frame getFramedQuestion()
    {
        Node framedQuestion = node.getSingleRelationship( IN_RESPONSE_TO, Direction.OUTGOING ).getEndNode();
        return new Frame(framedQuestion);
    }

    public void setAnswer( Answer answer )
    {
        node.createRelationshipTo( answer.node, PROPOSED_ANSWER_REL );
    }

    public Answer getAnswer()
    {
        Node answer = node.getSingleRelationship( PROPOSED_ANSWER_REL, Direction.OUTGOING ).getEndNode();
        return new Answer(answer);
    }
}
