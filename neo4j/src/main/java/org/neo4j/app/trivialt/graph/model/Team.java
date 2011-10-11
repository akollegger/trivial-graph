package org.neo4j.app.trivialt.graph.model;

import org.neo4j.graphdb.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Players compete in Teams.
 */
public class Team
{
    public static final String NAME_PROP = "name";

    private Node node;
    private static final RelationshipType MEMBER_REL = DynamicRelationshipType.withName( "member" );
    private static final RelationshipType OWNS_REL = DynamicRelationshipType.withName( "owns" );

    public Team( Node node )
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

    public void include( Player player )
    {
        player.node.createRelationshipTo( node, MEMBER_REL );
    }

    public Collection<Player> getPLayers()
    {
        Collection<Player> players = new ArrayList<Player>();
        for ( Relationship rel : node.getRelationships( MEMBER_REL, Direction.INCOMING))
        {
            players.add( new Player( rel.getEndNode() ) );
        }
        return players;
    }

    public void sign(Card card) {
        node.createRelationshipTo( card.node, OWNS_REL );
    }

    public Collection<Card> getCards()
    {
        Collection<Card> cards = new ArrayList<Card>();
        for ( Relationship rel : node.getRelationships( OWNS_REL, Direction.INCOMING))
        {
            cards.add( new Card( rel.getEndNode() ) );
        }
        return cards;
    }

}
