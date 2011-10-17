package org.neo4j.app.trivialt.graph.util;

import org.neo4j.graphdb.*;
import org.neo4j.graphdb.event.KernelEventHandler;
import org.neo4j.graphdb.event.TransactionEventHandler;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.util.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * SelfCleaningGraphDatabase creates an EmbeddedGraphDatabase at a particular directory,
 * which is shutdown and removed during shutdown.
 */
public class SelfCleaningGraphDatabase implements GraphDatabaseService
{
    EmbeddedGraphDatabase embeddedGraphDatabase;
    private String storedir;

    public SelfCleaningGraphDatabase( String storedir )
    {
        this.storedir = storedir;
        embeddedGraphDatabase = new EmbeddedGraphDatabase( storedir );
        registerShutdownHook();
    }

    private void registerShutdownHook()
    {
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                embeddedGraphDatabase.shutdown();
                try
                {
                    FileUtils.deleteRecursively( new File( storedir ) );
                } catch ( IOException e )
                {
                    e.printStackTrace();
                }

            }
        } );
    }

    @Override
    public Node createNode()
    {
        return embeddedGraphDatabase.createNode();
    }

    @Override
    public Node getNodeById( long id )
    {
        return embeddedGraphDatabase.getNodeById( id );
    }

    @Override
    public Relationship getRelationshipById( long id )
    {
        return embeddedGraphDatabase.getRelationshipById( id );
    }

    @Override
    public Node getReferenceNode()
    {
        return embeddedGraphDatabase.getReferenceNode();
    }

    @Override
    public Iterable<Node> getAllNodes()
    {
        return embeddedGraphDatabase.getAllNodes();
    }

    @Override
    public Iterable<RelationshipType> getRelationshipTypes()
    {
        return embeddedGraphDatabase.getRelationshipTypes();
    }

    @Override
    public void shutdown()
    {
        embeddedGraphDatabase.shutdown();
    }

    @Override
    public Transaction beginTx()
    {
        return embeddedGraphDatabase.beginTx();
    }

    @Override
    public <T> TransactionEventHandler<T> registerTransactionEventHandler( TransactionEventHandler<T> handler )
    {
        return embeddedGraphDatabase.registerTransactionEventHandler( handler );
    }

    @Override
    public <T> TransactionEventHandler<T> unregisterTransactionEventHandler( TransactionEventHandler<T> handler )
    {
        return embeddedGraphDatabase.unregisterTransactionEventHandler( handler );
    }

    @Override
    public KernelEventHandler registerKernelEventHandler( KernelEventHandler handler )
    {
        return embeddedGraphDatabase.registerKernelEventHandler( handler );
    }

    @Override
    public KernelEventHandler unregisterKernelEventHandler( KernelEventHandler handler )
    {
        return embeddedGraphDatabase.unregisterKernelEventHandler( handler );
    }

    @Override
    public IndexManager index()
    {
        return embeddedGraphDatabase.index();
    }
}
