package org.neo4j.app.trivialt.graph.model;

import org.neo4j.app.trivialt.graph.util.NodeMap;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Collection of Media.
 */
public class MediaLibrary
{
    private GraphDatabaseService graphdb;
    private NodeMap mediaMap;

    public MediaLibrary( GraphDatabaseService graphdb )
    {
        this.graphdb = graphdb;
        mediaMap = NodeMap.named( "media", graphdb);
    }

    public Collection<Media> getAll()
    {
        Collection<Media> allMedias = new ArrayList<Media>();
        for (Node qNode : mediaMap.values())
        {
            allMedias.add( new Media( qNode ) );
        }
        return allMedias;
    }

    public Media store( String url, String contentType )
    {
        Node node = mediaMap.get( url );
        Media media = null;
        if (node == null) {
            node = mediaMap.put( url, graphdb.createNode() );
            media = new Media(node);
            media.setUrl(url);
            media.setContentType( contentType );
        } else {
            media = new Media(node);
        }
        return media;
    }

}
