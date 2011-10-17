package org.neo4j.app.trivialt.model;

import org.neo4j.graphdb.*;
import org.springframework.data.neo4j.annotation.NodeEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * A Deck is the set of a Team's Cards for a particular Match.
 * 
 */
@NodeEntity
public class Deck
{
	private Match match;
	private Set<Card> cards;

    public Deck( ) {;}

    public Deck(Match forMatch) {
    	setMatch(forMatch);
    }

    public void add( Card card )
    {
    	cards.add(card);
    }

    public Iterable<Card> getProposals()
    {
        return cards;
    }

    public void setMatch( Match match)
    {
    	this.match = match;
    }

    public Match getMatch()
    {
    	return match;
    }

}
