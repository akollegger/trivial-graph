package org.neo4j.app.trivialt.graph.util;

import org.junit.Test;
import org.neo4j.app.trivialt.graph.Neo4jTestBase;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for NodeList
 */
public class NodeListTest extends Neo4jTestBase
{
    @Test
    public void shouldCreateNamedList()
    {
        NodeList list = NodeList.named( "test-list", graphdb );
        assertThat(list, is(not(nullValue())));
    }
}
