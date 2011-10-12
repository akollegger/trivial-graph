package org.neo4j.app.trivialt.graph.model;

import org.neo4j.graphdb.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * What up Playa?
 */
public class Player extends EntityBase
{
    private static final String HANDLE_PROP = "handle";
    private static final String NAME_PROP = "name";

    private static final RelationshipType KNOWS_REL = DynamicRelationshipType.withName( "friends" );

    public Player( Node node )
    {
        super( node );
    }

    public String getHandle()
    {
        return (String) node.getProperty( HANDLE_PROP, "" );
    }

    public void setHandle( String handle )
    {
        node.setProperty( HANDLE_PROP, handle);
    }

    public String getName()
    {
        return (String) node.getProperty( NAME_PROP, "" );
    }

    public void setName( String name )
    {
        node.setProperty(NAME_PROP, name);
    }

    public void friends( Player playerB )
    {
        node.createRelationshipTo( playerB.node, KNOWS_REL );
    }

    public Collection<Player> knownPlayers()
    {
        Collection<Player> knownPlayers = new ArrayList<Player>();
        for (Relationship rel : node.getRelationships( Direction.BOTH, KNOWS_REL ))
        {
            knownPlayers.add( new Player( rel.getOtherNode( node ) ) );
        }
        return knownPlayers;
    }

}
