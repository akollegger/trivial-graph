package org.neo4j.app.trivialt.graph.model;

import org.junit.Test;
import org.neo4j.app.trivialt.graph.Neo4jTestBase;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for Answers.
 */
public class AnswerTest extends Neo4jTestBase
{
    @Test
    public void shouldBeSameAnswerForSameText()
    {
        Answers answers = new Answers( graphdb );
        Answer a1 = answers.remember( "one" );
        Answer a2 = answers.remember( "one" );

        assertThat( a2, is( a1 ) );
    }

}
