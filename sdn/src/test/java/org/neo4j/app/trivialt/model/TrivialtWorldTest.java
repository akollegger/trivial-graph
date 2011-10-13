package org.neo4j.app.trivialt.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.app.trivialt.Neo4jTestBase;
import org.neo4j.app.trivialt.model.Category;
import org.neo4j.app.trivialt.model.FreeformTrivia;
import org.neo4j.app.trivialt.model.Question;
import org.neo4j.app.trivialt.service.TrivialtWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for TrivialtWorld.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/trivialt-test-context.xml"})
@Transactional
public class TrivialtWorldTest extends Neo4jTestBase
{
    @Autowired TrivialtWorld tw;

    @Test
    public void shouldLearnFreeformQuestions()
    {
        String questionText = "What does this test, test?";
        String answerText = "adding freeform questions";
        String categoryName = "test";
        FreeformTrivia fq = new FreeformTrivia( categoryName, questionText, answerText );

        tw.learn( fq );

        Iterable<Category> categories = tw.getAllCategories();
        assertThat (categories, is(not(nullValue())));
        Category testCategory = categories.iterator().next();
        assertThat( testCategory.getName(), is( categoryName ) );

        Question q = tw.findQuestion( questionText );
        assertThat( q.getText(), is( questionText ) );

        assertThat( q, is( not( nullValue() ) ) );
        String answer = q.getAnswer().getText();
        assertThat( answer, is( answerText ) );
    }
}
