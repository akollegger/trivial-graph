package org.neo4j.app.trivialt.graph.model;

import org.neo4j.graphdb.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Media links to an image, video or music.
 */
public class Media
{
    private static final String URL_PROP = "id";
    private static final String CONTENT_TYPE_PROP = "content_type";

    private static final RelationshipType RENDER_REL = DynamicRelationshipType.withName( "renders" );

    Node node;

    public Media( Node node )
    {
        this.node = node;
    }

    public String getUrl()
    {
        return (String) node.getProperty( URL_PROP, "" );
    }

    public void setUrl( String id )
    {
        node.setProperty( URL_PROP, id);
    }

    public String getContentType()
    {
        return (String) node.getProperty( CONTENT_TYPE_PROP, "" );
    }

    public void setContentType( String contentType )
    {
        node.setProperty( CONTENT_TYPE_PROP, contentType);
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        Media player = (Media) o;

        return (node.getId() == player.node.getId());
    }

    @Override
    public int hashCode()
    {
        return node != null ? node.hashCode() : 0;
    }
}
