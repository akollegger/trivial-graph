package org.neo4j.app.trivialt.model;

import org.neo4j.graphdb.*;
import org.springframework.data.neo4j.annotation.NodeEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * A Team uses a Card to record their proposed trivia Answers for a Round
 */
@NodeEntity
public class Card
{
	private boolean locked;
	
	private Round round;
	
	private Set<Proposal> proposals;

    public Card( ) {;}

    public Card(Round forRound) {
    	this.round = forRound;
    }
    
    public boolean isLocked()
    {
    	return locked;
    }

    public void setLocked( boolean locked )
    {
    	this.locked = locked;
    }

    public void record( Proposal proposal )
    {
    	proposals.add(proposal);
    }

    public Iterable<Proposal> getProposals()
    {
        return proposals;
    }

    public void setRound( Round round)
    {
    	this.round = round;
    }

    public Round getRound()
    {
    	return round;
    }

}
