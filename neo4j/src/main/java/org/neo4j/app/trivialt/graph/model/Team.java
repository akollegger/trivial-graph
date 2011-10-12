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
    public static final String SECRET_PROP = "secret";
    private static final String FOUNDER_RELPROP = "is_founder";

    private Node node;
    private static final RelationshipType MEMBER_REL = DynamicRelationshipType.withName( "member" );
    private static final RelationshipType ASSIGNED_REL = DynamicRelationshipType.withName( "assigned" );

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

    public String getSecret()
    {
        return (String) node.getProperty( SECRET_PROP, "" );
    }

    public void setSecret( String secret )
    {
        node.setProperty( SECRET_PROP, secret );
    }

    public void foundedBy(Player founder) {
        Relationship rel = founder.node.createRelationshipTo( node, MEMBER_REL );
        rel.setProperty( FOUNDER_RELPROP, true );
    }

    public void include( Player player )
    {
        player.node.createRelationshipTo( node, MEMBER_REL );
    }

    public Collection<Player> getPlayers()
    {
        Collection<Player> players = new ArrayList<Player>();
        for ( Relationship rel : node.getRelationships( MEMBER_REL, Direction.INCOMING))
        {
            players.add( new Player( rel.getEndNode() ) );
        }
        return players;
    }

    public void sign(Card card) {
        node.createRelationshipTo( card.node, ASSIGNED_REL );
    }

    public Collection<Card> getCards()
    {
        Collection<Card> cards = new ArrayList<Card>();
        for ( Relationship rel : node.getRelationships( ASSIGNED_REL, Direction.OUTGOING))
        {
            cards.add( new Card( rel.getEndNode() ) );
        }
        return cards;
    }

    public Player getFounder()
    {
        Player founder =null;
        for ( Relationship rel : node.getRelationships( MEMBER_REL, Direction.INCOMING))
        {
            if ((Boolean)rel.getProperty( FOUNDER_RELPROP, false )) {
                founder = new Player(rel.getStartNode());
                break;
            }
        }
        return founder;
    }
}
