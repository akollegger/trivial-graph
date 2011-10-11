package org.neo4j.app.trivialt.graph.model;

import org.neo4j.app.trivialt.graph.util.NodeMap;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Collection of players.
 */
public class Players
{
    private GraphDatabaseService graphdb;
    private NodeMap playerMap;

    public Players( GraphDatabaseService graphdb )
    {
        this.graphdb = graphdb;
        playerMap = NodeMap.named( "players", graphdb);
    }

    public Collection<Player> getAll()
    {
        Collection<Player> allPlayers = new ArrayList<Player>();
        for (Node qNode : playerMap.values())
        {
            allPlayers.add( new Player( qNode ) );
        }
        return allPlayers;
    }

    public Player register( String name, String id )
    {
        Node node = playerMap.get( id );
        Player player = null;
        if (node == null) {
            node = playerMap.put( id, graphdb.createNode() );
            player = new Player(node);
            player.setName(name);
            player.setId( id );
        } else {
            player = new Player(node);
        }
        return player;
    }

}
