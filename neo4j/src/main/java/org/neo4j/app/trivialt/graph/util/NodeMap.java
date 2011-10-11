package org.neo4j.app.trivialt.graph.util;

import org.neo4j.graphdb.*;

import java.util.*;

/**
 * Node-based Map.
 * <p/>
 * Pattern:
 * <p/>
 * (refNode)-[:MAPS]->(maps)-[:MAP_INSTANCE]->(map)-[:VALUE.key]->(value)
 */
public class NodeMap implements Map<String, Node>
{
    public static final RelationshipType MAPS_REL = DynamicRelationshipType.withName( "MAPS" );
    public static final RelationshipType MAP_INSTANCE_REL = DynamicRelationshipType.withName( "MAP_INSTANCE" );
    private static final RelationshipType VALUE_REL = DynamicRelationshipType.withName( "VALUE" );

    public static final String MAP_NAME_PROP = "map_name";
    private static final String KEY_PROP = "key";

    private GraphDatabaseService graphdb;
    private Node root;

    private NodeMap( Node root )
    {
        this.root = root;
        this.graphdb = root.getGraphDatabase();
    }

    @Override
    public int size()
    {
        int countedSize = 0;
        for ( Relationship keyRel : root.getRelationships( VALUE_REL, Direction.OUTGOING ) )
        {
            countedSize++;
        }

        return countedSize;
    }

    @Override
    public boolean isEmpty()
    {
        return (!root.getRelationships( VALUE_REL ).iterator().hasNext());
    }

    @Override
    public boolean containsKey( Object o )
    {
        boolean doesContainKey = false;
        for ( Relationship keyRel : root.getRelationships( VALUE_REL, Direction.OUTGOING ) )
        {
            if ( keyRel.getProperty( KEY_PROP ).equals( o ) )
            {
                doesContainKey = true;
                break;
            }
        }
        return doesContainKey;
    }

    @Override
    public boolean containsValue( Object o )
    {
        boolean doesContainValue = false;
        for ( Relationship keyRel : root.getRelationships( VALUE_REL, Direction.OUTGOING ) )
        {
            if ( keyRel.getEndNode().equals( o ) )
            {
                doesContainValue = true;
                break;
            }
        }

        return doesContainValue;
    }

    @Override
    public Node get( Object key )
    {
        Relationship keyRel = findValueRel( key.toString() );
        if ( keyRel != null )
        {
            return keyRel.getEndNode();
        }

        return null;
    }

    @Override
    public Node put( String key, Node node )
    {
        Relationship keyRel = findValueRel( key );

        // prune existing mapped node
        if ( keyRel != null )
        {
            keyRel.delete();
        }

        keyRel = root.createRelationshipTo( node, VALUE_REL );
        keyRel.setProperty( KEY_PROP, key );
        return node;
    }

    private Relationship findValueRel( String key )
    {
        Relationship keyRel = null;
        for ( Relationship possibleRel : root.getRelationships( VALUE_REL, Direction.OUTGOING ) )
        {
            if ( possibleRel.getProperty( KEY_PROP ).equals( key ) )
            {
                keyRel = possibleRel;
                break;
            }
        }
        return keyRel;

    }

    @Override
    public Node remove( Object o )
    {
        Node removedNode = null;
        for ( Relationship keyRel : root.getRelationships( VALUE_REL, Direction.OUTGOING ) )
        {
            if ( keyRel.getEndNode().equals( o ) )
            {
                removedNode = keyRel.getEndNode();
                keyRel.delete();
                break;
            }
        }
        return removedNode;
    }

    @Override
    public void putAll( Map<? extends String, ? extends Node> map )
    {
        throw new UnsupportedOperationException( "not yet implemented" );
    }

    @Override
    public void clear()
    {
        for ( Relationship keyRel : root.getRelationships( VALUE_REL, Direction.OUTGOING ) )
        {
            keyRel.delete();
        }
    }

    @Override
    public Set<String> keySet()
    {
        Set<String> collectedKeys = new HashSet<String>();
        for ( Relationship keyRel : root.getRelationships( VALUE_REL, Direction.OUTGOING ) )
        {
            collectedKeys.add( (String) keyRel.getProperty( KEY_PROP ) );
        }

        return collectedKeys;
    }

    @Override
    public Collection<Node> values()
    {
        ArrayList<Node> collectedNodes = new ArrayList<Node>();
        for ( Relationship keyRel : root.getRelationships( VALUE_REL, Direction.OUTGOING ) )
        {
            collectedNodes.add( keyRel.getEndNode() );
        }

        return collectedNodes;
    }

    @Override
    public Set<Map.Entry<String, Node>> entrySet()
    {
        Set<Map.Entry<String, Node>> entries = new HashSet<Map.Entry<String, Node>>();
        for ( Relationship keyRel : root.getRelationships( VALUE_REL, Direction.OUTGOING ) )
        {
            entries.add( new Entry<String, Node>( (String) keyRel.getProperty( KEY_PROP ), keyRel.getEndNode() ) );
        }

        return entries;
    }

    public static NodeMap named( String mapName, GraphDatabaseService inGraphDatabase )
    {
        Node referenceNode = inGraphDatabase.getReferenceNode();
        if ( referenceNode == null )
            return null;

        Relationship mapsRel = referenceNode.getSingleRelationship( MAPS_REL, Direction.OUTGOING );
        Node maps = null;
        if ( mapsRel == null )
        {
            maps = inGraphDatabase.createNode();
            referenceNode.createRelationshipTo( maps, MAPS_REL );
        }
        else
        {
            maps = mapsRel.getEndNode();
        }

        Node requestedMap = null;
        for ( Relationship possibleMapRel : maps.getRelationships( MAP_INSTANCE_REL, Direction.OUTGOING ) )
        {
            if ( possibleMapRel.getProperty( MAP_NAME_PROP ).equals( mapName ) )
            {
                requestedMap = possibleMapRel.getEndNode();
                break;
            }
        }

        if ( requestedMap == null )
        {
            requestedMap = inGraphDatabase.createNode();
            Relationship newMapRel = maps.createRelationshipTo( requestedMap, MAP_INSTANCE_REL );
            newMapRel.setProperty( MAP_NAME_PROP, mapName );
        }

        return new NodeMap( requestedMap );
    }

    private class Entry<K, V> implements Map.Entry<K, V>
    {
        K key;
        V value;

        public Entry( K key, V value )
        {
            this.key = key;
            this.value = value;
        }

        public K getKey()
        {
            return key;
        }

        public V getValue()
        {
            return value;
        }

        public Node setValue( Object value )
        {
            throw new UnsupportedOperationException( "not yet implemented" );
        }
    }
}
