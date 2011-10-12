package org.neo4j.app.trivialt.graph.model;

import org.neo4j.graphdb.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A Team uses a Card to record their proposed trivia answers.
 */
public class Card
{
    // once a card is locked, it can't be changed
    private static final String LOCKED_PROP = "locked";

    Node node;
    private static final RelationshipType HAS_PROPOSAL_REL = DynamicRelationshipType.withName( "CONTAINS" );
    private static final RelationshipType SUBMITTED_TO_REL = DynamicRelationshipType.withName( "SUBMITTED_TO" );

    public Card( Node node )
    {
        this.node = node;
    }

    public boolean isLocked()
    {
        return (Boolean)node.getProperty( LOCKED_PROP, false );
    }

    public void setLocked( boolean locked )
    {
        node.setProperty(LOCKED_PROP, locked);
    }

    public void record( Proposal proposal )
    {
        node.createRelationshipTo( proposal.node, HAS_PROPOSAL_REL );
        node.setProperty(LOCKED_PROP, true);
    }

    public Collection<Proposal> getProposals()
    {
        Collection<Proposal> proposals = new ArrayList<Proposal>();
        for ( Relationship rel : node.getRelationships( HAS_PROPOSAL_REL, Direction.OUTGOING))
        {
            proposals.add( new Proposal( rel.getEndNode() ) );
        }
        return proposals;
    }

    public void submitTo( Round round)
    {
        node.createRelationshipTo( round.node, SUBMITTED_TO_REL );
    }

    public Round getRound()
    {
        Round foundRound = null;
        Relationship roundRel = node.getSingleRelationship( SUBMITTED_TO_REL, Direction.OUTGOING );
        if (roundRel != null) {
            foundRound = new Round(roundRel.getEndNode());
        }
        return foundRound;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        Card player = (Card) o;

        return (node.getId() == player.node.getId());
    }

    @Override
    public int hashCode()
    {
        return node != null ? node.hashCode() : 0;
    }
}
