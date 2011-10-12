package org.neo4j.app.trivialt.graph.util;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.app.trivialt.graph.Neo4jTestBase;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.internal.matchers.IsCollectionContaining.hasItems;

/**
 * Unit tests for NodeMap
 */
public class NodeMapTest extends Neo4jTestBase
{

    private Transaction tx;

    @Test
    public void shouldCreateMapNavigableFromReferenceNode()
    {
        Map<String, Node> nodeMap = NodeMap.named( "test", graphdb );

        assertThat( nodeMap, is( not( nullValue() ) ) );

        ExecutionEngine engine = new ExecutionEngine( graphdb );
        //ExecutionResult result = engine.execute( "start ref=node(0) match (ref)-[:MAPS]->(maps) return maps" );
        ExecutionResult result = engine.execute( "start ref=node(0) match (ref)-[:MAPS]->(maps)-[r:MAP_INSTANCE]->(map) where r.map_name=\"test\" return map" );
        Node queriedMap = (Node) result.columnAs( "map" ).next();
        assertThat( queriedMap, is( not( nullValue() ) ) );
    }

    @Test
    public void shouldSupportPutAndGet()
    {
        Map<String, Node> nodeMap = NodeMap.named( "withValue", graphdb );
        Node mappedNode = graphdb.createNode();
        final String nodeKey = "nodeKey";
        nodeMap.put( nodeKey, mappedNode );
        Node foundNode = nodeMap.get( nodeKey );

        assertThat( foundNode, is( mappedNode ) );
    }

    @Test
    public void shouldUpdateMap()
    {
        Map<String, Node> nodeMap = NodeMap.named( "withValue", graphdb );
        Node mappedNode1 = graphdb.createNode();
        final String nodeKey = "nodeKey";
        nodeMap.put( nodeKey, mappedNode1 );

        Node mappedNode2 = graphdb.createNode();
        nodeMap.put( nodeKey, mappedNode2 );

        Node foundNode = nodeMap.get( nodeKey );

        assertThat( foundNode, is( mappedNode2 ) );
    }

    @Test
    public void shouldSupportKeySet()
    {
        Map<String, Node> nodeMap = NodeMap.named( "keysetted", graphdb );
        Node mappedNode1 = graphdb.createNode();
        final String nodeKey1 = "nodeKey1";
        nodeMap.put( nodeKey1, mappedNode1 );

        Node mappedNode2 = graphdb.createNode();
        final String nodeKey2 = "nodeKey2";
        nodeMap.put( nodeKey2, mappedNode2 );

        assertThat( nodeMap.keySet(), hasItems( nodeKey1, nodeKey2 ) );

    }

    @Test
    public void shouldSupportValues()
    {
        Map<String, Node> nodeMap = NodeMap.named( "valued", graphdb );
        Node mappedNode1 = graphdb.createNode();
        final String nodeKey1 = "nodeKey1";
        nodeMap.put( nodeKey1, mappedNode1 );

        Node mappedNode2 = graphdb.createNode();
        final String nodeKey2 = "nodeKey2";
        nodeMap.put( nodeKey2, mappedNode2 );

        assertThat( nodeMap.values(), hasItems( mappedNode1, mappedNode2 ) );
    }


    @Test
    public void shouldSupportEntrySet()
    {
        Map<String, Node> nodeMap = NodeMap.named( "entrysetted", graphdb );
        Node mappedNode1 = graphdb.createNode();
        final String nodeKey1 = "nodeKey1";
        nodeMap.put( nodeKey1, mappedNode1 );

        Node mappedNode2 = graphdb.createNode();
        final String nodeKey2 = "nodeKey2";
        nodeMap.put( nodeKey2, mappedNode2 );

        Set<Map.Entry<String, Node>> entries = nodeMap.entrySet();
        boolean found1 = false;
        boolean found2 = false;
        for ( Map.Entry entry : entries )
        {
            if ( entry.getKey().equals( nodeKey1 ) && entry.getValue().equals( mappedNode1 ) )
                found1 = true;
            if ( entry.getKey().equals( nodeKey2 ) && entry.getValue().equals( mappedNode2 ) )
                found2 = true;
        }
        assertTrue( found1 && found2 );
    }

    @Test
    public void shouldSupportSizeEmptinessAndMisc()
    {
        Map<String, Node> nodeMap = NodeMap.named( "misc", graphdb );
        Node mappedNode1 = graphdb.createNode();
        final String nodeKey1 = "nodeKey1";
        nodeMap.put( nodeKey1, mappedNode1 );

        Node mappedNode2 = graphdb.createNode();
        final String nodeKey2 = "nodeKey2";
        nodeMap.put( nodeKey2, mappedNode2 );

        assertThat( nodeMap.size(), is( 2 ) );
        assertFalse( nodeMap.isEmpty() );
        assertTrue( nodeMap.containsKey( nodeKey1 ) );
        assertTrue( nodeMap.containsKey( nodeKey2 ) );
        assertTrue( nodeMap.containsValue( mappedNode1 ) );
        assertTrue( nodeMap.containsValue( mappedNode2 ) );
    }

    @Test
    public void shouldRemoveValues()
    {
        Map<String, Node> nodeMap = NodeMap.named( "keysetted", graphdb );
        Node mappedNode1 = graphdb.createNode();
        final String nodeKey1 = "nodeKey1";
        nodeMap.put( nodeKey1, mappedNode1 );

        assertTrue( nodeMap.containsValue( mappedNode1 ) );

        nodeMap.remove( mappedNode1 );

        assertFalse( nodeMap.containsValue( mappedNode1 ) );
    }

    @Test
    public void shouldClear()
    {
        Map<String, Node> nodeMap = NodeMap.named( "clearable", graphdb );
        Node mappedNode1 = graphdb.createNode();
        final String nodeKey1 = "nodeKey1";
        nodeMap.put( nodeKey1, mappedNode1 );
        Node mappedNode2 = graphdb.createNode();
        final String nodeKey2 = "nodeKey2";
        nodeMap.put( nodeKey2, mappedNode2 );

        nodeMap.clear();

        assertTrue( nodeMap.isEmpty() );
    }

}
