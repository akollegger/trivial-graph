package org.neo4j.collections.radixtree;

import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.*;
import org.neo4j.test.ImpermanentGraphDatabase;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;

/**
 * Test-based learning of the radix tree.
 */
public class TrieTry
{
    static GraphDatabaseService graphdb = null;

    @BeforeClass
    public static void setupTests() throws IOException
    {
        graphdb = new ImpermanentGraphDatabase();
    }

    @Test
    public void checkDatabase()
    {
        Transaction tx = graphdb.beginTx();
        try
        {
            graphdb.createNode();
        } finally
        {
            tx.finish();
        }
    }

    @Test
    public void createTrie()
    {
        Node ref = graphdb.getNodeById( 0 );
        Transaction tx = graphdb.beginTx();
        try
        {
            RadixTree trie = new RadixTreeImpl( graphdb, ref );
        } finally
        {
            tx.finish();
        }
    }

    @Test
    public void createTrieAndInsertValues()
    {
        Node ref = graphdb.getNodeById( 0 );
        Transaction tx = graphdb.beginTx();
        try
        {
            RadixTree trie = new RadixTreeImpl( graphdb, ref );
            Node valueNode = graphdb.createNode();
            trie.insert( "foo", valueNode );
            valueNode = graphdb.createNode();
            trie.insert( "food", valueNode );
            valueNode = graphdb.createNode();
            trie.insert( "fool", valueNode );
            Node preLeaf = valueNode.getSingleRelationship( RadixTreeRelationshipTypes.RADIXTREE_LEAF, Direction.INCOMING ).getStartNode();
            System.out.println( preLeaf.getProperty( RadixTreeImpl.RADIXTREE_LABEL ) );
        } finally
        {
            tx.finish();
        }
    }


    @Test
    public void createTwoTries()
    {
        Node ref = graphdb.getNodeById( 0 );
        Transaction tx = graphdb.beginTx();
        try
        {
            RadixTree trie1 = new RadixTreeImpl( graphdb, ref );
            Node valueNode = graphdb.createNode();
            trie1.insert( "foo", valueNode );
            valueNode = graphdb.createNode();
            trie1.insert( "food", valueNode );
            Node foolNode = graphdb.createNode();
            trie1.insert( "fool", foolNode );
            Node preLeaf = valueNode.getSingleRelationship( RadixTreeRelationshipTypes.RADIXTREE_LEAF, Direction.INCOMING ).getStartNode();
            System.out.println( preLeaf.getProperty( RadixTreeImpl.RADIXTREE_LABEL ) );

            RadixTree trie2 = new RadixTreeImpl( graphdb, ref );
            valueNode = graphdb.createNode();
            trie2.insert( "fin", valueNode );
            valueNode = graphdb.createNode();
            trie2.insert( "finger", valueNode );
            Node firNode = graphdb.createNode();
            trie2.insert( "fir", firNode );
            Node preLeaf2 = valueNode.getSingleRelationship( RadixTreeRelationshipTypes.RADIXTREE_LEAF, Direction.INCOMING ).getStartNode();
            System.out.println( preLeaf2.getProperty( RadixTreeImpl.RADIXTREE_LABEL ) );

            assertThat( trie1.get("fool"), hasItem(foolNode) );
            assertTrue( trie1.get("fir").isEmpty() );

            assertThat( trie2.get("fir"), hasItem(firNode) );
            assertTrue( trie2.get( "fool" ).isEmpty() );
        } finally
        {
            tx.finish();
        }
    }
}
