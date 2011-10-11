package org.neo4j.app.trivialt.graph.util;

import org.neo4j.graphdb.*;

import java.util.*;

/**
 * Linked list of Nodes.
 * <p/>
 * Pattern:
 * <p/>
 * (refNode)--[:LISTS]-->(lists)--[:LIST_INSTANCE]-->(list)--[:HEAD]-->(head)
 */
public class NodeList implements List<Node>
{
    public static final RelationshipType LISTS_REL = DynamicRelationshipType.withName( "LISTS" );
    public static final RelationshipType LIST_INSTANCE_REL = DynamicRelationshipType.withName( "LIST_INSTANCE" );
    private static final RelationshipType HEAD_REL = DynamicRelationshipType.withName( "HEAD" );
    private static final RelationshipType NEXT_REL = DynamicRelationshipType.withName( "NEXT" );

    public static final String LIST_NAME_PROP = "name";

    private GraphDatabaseService graphdb;
    private Node list;

    private NodeList( Node list )
    {
        this.graphdb = list.getGraphDatabase();
        this.list = list;
    }

    @Override
    public int size()
    {
        int countedSize = 0;
        Relationship headRel = list.getSingleRelationship( HEAD_REL, Direction.OUTGOING );
        if ( headRel != null )
        {
            Node entry = headRel.getEndNode();
            countedSize++;
            Relationship nextRel = entry.getSingleRelationship( NEXT_REL, Direction.OUTGOING );
            while ( nextRel != null )
            {
                countedSize++;
                nextRel = nextRel.getEndNode().getSingleRelationship( NEXT_REL, Direction.OUTGOING );
            }
        }

        return countedSize;
    }

    @Override
    public boolean isEmpty()
    {
        return (list.getSingleRelationship( HEAD_REL, Direction.OUTGOING ) == null);
    }

    @Override
    public boolean contains( Object o )
    {
        Relationship headRel = list.getSingleRelationship( HEAD_REL, Direction.OUTGOING );
        if ( headRel != null )
        {
            Node entry = headRel.getEndNode();
            if ( entry.equals( o ) )
                return true;
            Relationship nextRel = entry.getSingleRelationship( NEXT_REL, Direction.OUTGOING );
            while ( nextRel != null )
            {
                entry = nextRel.getEndNode();
                if ( entry.equals( o ) )
                    return true;
                nextRel = entry.getSingleRelationship( NEXT_REL, Direction.OUTGOING );
            }
        }
        return false;
    }

    @Override
    public Iterator<Node> iterator()
    {
        return new NodeListIterator();
    }

    @Override
    public Object[] toArray()
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public <T> T[] toArray( T[] ts )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public boolean add( Node node )
    {
        Relationship headRel = list.getSingleRelationship( HEAD_REL, Direction.OUTGOING );
        if ( headRel != null )
        {
            Node entry = headRel.getEndNode();
            Relationship nextRel = entry.getSingleRelationship( NEXT_REL, Direction.OUTGOING );
            while ( nextRel != null )
            {
                entry = nextRel.getEndNode();
                nextRel = entry.getSingleRelationship( NEXT_REL, Direction.OUTGOING );
            }
            entry.createRelationshipTo( node, NEXT_REL );
        } else {
            list.createRelationshipTo( node, HEAD_REL );
        }
        return true;
    }

    @Override
    public boolean remove( Object o )
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean containsAll( Collection<?> objects )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public boolean addAll( Collection<? extends Node> nodes )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public boolean addAll( int i, Collection<? extends Node> nodes )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public boolean removeAll( Collection<?> objects )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public boolean retainAll( Collection<?> objects )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public Node get( int i )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public Node set( int i, Node node )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public void add( int i, Node node )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public Node remove( int i )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public int indexOf( Object o )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public int lastIndexOf( Object o )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public ListIterator<Node> listIterator()
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public ListIterator<Node> listIterator( int i )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    @Override
    public List<Node> subList( int i, int i1 )
    {
        throw new UnsupportedOperationException( "TBD" );
    }

    private class NodeListIterator implements Iterator<Node>
    {
        private Relationship next;

        public NodeListIterator()
        {
            this.next = list.getSingleRelationship( HEAD_REL, Direction.OUTGOING );
        }

        @Override
        public boolean hasNext()
        {
            return (next != null);
        }

        @Override
        public Node next()
        {
            Node nextNode = next.getEndNode();
            next = nextNode.getSingleRelationship( NEXT_REL, Direction.OUTGOING );
            return nextNode;
        }

        @Override
        public void remove()
        {
            throw new UnsupportedOperationException("TBD");
        }
    }

    public static NodeList named( String listName, GraphDatabaseService inGraphDatabase )
    {
        Node referenceNode = inGraphDatabase.getReferenceNode();
        if ( referenceNode == null )
            return null;

        Relationship listsRel = referenceNode.getSingleRelationship( LISTS_REL, Direction.OUTGOING );
        Node lists = null;
        if ( listsRel == null )
        {
            lists = inGraphDatabase.createNode();
            referenceNode.createRelationshipTo( lists, LISTS_REL );
        }
        else
        {
            lists = listsRel.getEndNode();
        }

        Node requestedList = null;
        for ( Relationship possibleListRel : lists.getRelationships( LIST_INSTANCE_REL, Direction.OUTGOING ) )
        {
            if ( possibleListRel.getProperty( LIST_NAME_PROP ).equals( listName ) )
            {
                requestedList = possibleListRel.getEndNode();
                break;
            }
        }

        if ( requestedList == null )
        {
            requestedList = inGraphDatabase.createNode();
            Relationship newListMRel = lists.createRelationshipTo( requestedList, LIST_INSTANCE_REL );
            newListMRel.setProperty( LIST_NAME_PROP, listName );
        }

        return new NodeList( requestedList );
    }

    public static NodeList rootedAt( Node list)
    {
        GraphDatabaseService graphdb = list.getGraphDatabase();
        Node referenceNode = graphdb.getReferenceNode();
        if ( referenceNode == null )
            return null;

        Relationship listsRel = referenceNode.getSingleRelationship( LISTS_REL, Direction.OUTGOING );
        Node lists = null;
        if ( listsRel == null )
        {
            lists = graphdb.createNode();
            referenceNode.createRelationshipTo( lists, LISTS_REL );
        }
        else
        {
            lists = listsRel.getEndNode();
        }

        Node requestedList = null;
        for ( Relationship possibleListRel : lists.getRelationships( LIST_INSTANCE_REL, Direction.OUTGOING ) )
        {
            if ( possibleListRel.getEndNode().getId() == list.getId() )
            {
                requestedList = possibleListRel.getEndNode();
                break;
            }
        }

        if ( requestedList == null )
        {
            requestedList = list;
            lists.createRelationshipTo( requestedList, LIST_INSTANCE_REL );
        }

        return new NodeList( requestedList );
    }
}
