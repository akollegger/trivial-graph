package org.neo4j.app.trivialt.graph.model;

import org.neo4j.graphdb.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * What up Playa?
 */
public class Player extends EntityBase
{
    private static final String ID_PROP = "id";
    private static final String NAME_PROP = "name";

    private static final RelationshipType KNOWS_REL = DynamicRelationshipType.withName( "knows" );

    public Player( Node node )
    {
        super( node );
    }

    public String getId()
    {
        return (String) node.getProperty( ID_PROP, "" );
    }

    public void setId( String id )
    {
        node.setProperty(ID_PROP, id);
    }

    public String getName()
    {
        return (String) node.getProperty( NAME_PROP, "" );
    }

    public void setName( String name )
    {
        node.setProperty(NAME_PROP, name);
    }

    public void knows(Player playerB)
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
