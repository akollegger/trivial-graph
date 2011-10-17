package org.neo4j.app.trivialt;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.neo4j.graphdb.Transaction;
import org.neo4j.test.ImpermanentGraphDatabase;

import java.io.IOException;

/**
 * Base for Neo4j unit tests.
 */
public class Neo4jTestBase
{
    public static ImpermanentGraphDatabase graphdb;
    private Transaction tx;

    @BeforeClass
    public static void setup() throws IOException
    {
        graphdb = new ImpermanentGraphDatabase();
    }

    @AfterClass
    public static void teardown()
    {
        graphdb.shutdown();
    }

    @Before
    public void startTransaction()
    {
        tx = graphdb.beginTx();
    }

    @After
    public void endTransaction()
    {
        tx.finish();
    }

}
