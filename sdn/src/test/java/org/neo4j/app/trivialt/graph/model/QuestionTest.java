package org.neo4j.app.trivialt.graph.model;

import org.junit.Test;
import org.neo4j.app.trivialt.graph.Neo4jTestBase;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;

/**
 * Unit tests for Questions.
 */
public class QuestionTest extends Neo4jTestBase
{
    @Test
    public void shouldOnlyHaveOneAnswer()
    {
        Answers answers = new Answers(graphdb);
        Answer a = answers.remember( "one" );
        Questions questions = new Questions(graphdb);
        Question q = questions.remember( "how many answers to this question",  a);

        assertThat( q.getAnswer(), is( a ) );

        Answer a2 = answers.remember( "1" );
        q.setAnswer( a2 );
        assertThat( q.getAnswer(), is( a2 ) );
    }

}
