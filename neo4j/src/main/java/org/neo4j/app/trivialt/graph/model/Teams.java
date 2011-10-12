package org.neo4j.app.trivialt.graph.model;

import org.neo4j.app.trivialt.graph.util.NodeMap;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Collection of teams.
 */
public class Teams
{
    private GraphDatabaseService graphdb;
    private NodeMap teamMap;

    public Teams( GraphDatabaseService graphdb )
    {
        this.graphdb = graphdb;
        teamMap = NodeMap.named( "teams", graphdb);
    }

    public Collection<Team> getAll()
    {
        Collection<Team> allTeams = new ArrayList<Team>();
        for (Node node : teamMap.values())
        {
            allTeams.add( new Team( node ) );
        }
        return allTeams;
    }

    public Team establish( Player founder, String name, String secret )
    {
        Node node = teamMap.get( name );
        Team team = null;
        if (node == null) {
            node = teamMap.put( name, graphdb.createNode() );
            team = new Team(node);
            team.setName(name);
            team.setSecret( secret );
        } else {
            team = new Team(node);
            Player existingFounder = team.getFounder();
            if ((existingFounder != null) && !team.getFounder().equals(founder)) team = null;
        }
        return team;
    }

}
