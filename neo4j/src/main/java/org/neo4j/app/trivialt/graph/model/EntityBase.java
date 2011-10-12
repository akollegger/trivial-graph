package org.neo4j.app.trivialt.graph.model;

import org.neo4j.graphdb.Node;

/**
 * Base class for entities.
 */
public abstract class EntityBase
{
    Node node;

    public EntityBase( Node node )
    {
        this.node = node;
    }

    @Override
    public boolean equals( Object o )
    {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        EntityBase other = (EntityBase) o;

        return (node.getId() == other.node.getId());
    }

    @Override
    public int hashCode()
    {
        return node != null ? node.hashCode() : 0;
    }
}
