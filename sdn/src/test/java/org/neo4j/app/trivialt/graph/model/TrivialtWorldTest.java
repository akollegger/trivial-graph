package org.neo4j.app.trivialt.graph.model;

import org.junit.Test;
import org.neo4j.app.trivialt.graph.Neo4jTestBase;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for TrivialtWorld.
 */
public class TrivialtWorldTest extends Neo4jTestBase
{
    TrivialtWorld tw;

    @Test
    public void shouldLearnFreeformQuestions()
    {
        this.tw = new TrivialtWorld( graphdb );

        String questionText = "What does this test, test?";
        String answerText = "Adding freeform questions";
        String categoryName = "test";
        FreeformTrivia fq = new FreeformTrivia( categoryName, questionText, answerText );

        tw.learn( fq );

        Collection<Category> categories = tw.getAllCategories();
        Category testCategory = categories.iterator().next();
        assertThat( testCategory.getName(), is( categoryName ) );

        Question q = tw.findQuestion( questionText );
        assertThat( q.getText(), is( questionText ) );

        assertThat( q, is( not( nullValue() ) ) );
        String answer = tw.answer( questionText );
        assertThat( answer, is( answerText ) );
    }
}
