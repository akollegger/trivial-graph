package org.neo4j.app.trivialt.graph.model;

import org.neo4j.app.trivialt.graph.util.NodeList;
import org.neo4j.graphdb.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A Round poses a series of Framed Questions.
 */
public class Round
{
    private static final RelationshipType FRAME_LIST_REL = DynamicRelationshipType.withName( "frame_list" );
    private static final RelationshipType FRAME_REL = DynamicRelationshipType.withName( "frame" );

    public static final String TITLE_PROP = "title";
    public static final String DIFFICULTY_PROP = "difficulty";

    Node node;
    private NodeList frames;

    public Round( Node node )
    {
        this.node = node;
        Relationship listRel = node.getSingleRelationship( FRAME_LIST_REL, Direction.OUTGOING );
        Node list = null;
        if ( listRel != null )
        {
            frames = NodeList.rootedAt( list );
        }
        else
        {
            frames = NodeList.rootedAt( node.getGraphDatabase().createNode() );
        }
    }

    public String getTitle()
    {
        return (String) node.getProperty( TITLE_PROP, "" );
    }

    public void setTitle( String title )
    {
        node.setProperty( TITLE_PROP, title );
    }

    public String getDifficulty()
    {
        return (String) node.getProperty( DIFFICULTY_PROP, "indeterminate" );
    }

    public void setDifficulty( String difficulty )
    {
        node.setProperty( DIFFICULTY_PROP, difficulty );
    }

    public void add( Frame framedQuestion )
    {
        node.createRelationshipTo( framedQuestion.node, FRAME_REL );
    }

    public Collection<Frame> getFrames()
    {
        Collection<Frame> frames = new ArrayList<Frame>();
        for ( Relationship rel : node.getRelationships( FRAME_REL, Direction.OUTGOING ) )
        {
            frames.add( new Frame( rel.getEndNode() ) );
        }
        return frames;
    }
}
