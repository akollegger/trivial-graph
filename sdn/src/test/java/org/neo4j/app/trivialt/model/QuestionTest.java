package org.neo4j.app.trivialt.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.app.trivialt.Neo4jTestBase;
import org.neo4j.app.trivialt.repository.Answers;
import org.neo4j.app.trivialt.repository.Questions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;

/**
 * Unit tests for Questions.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/trivialt-test-context.xml"})
@Transactional
public class QuestionTest extends Neo4jTestBase
{

    @Autowired private Answers answers;
    @Autowired private Questions questions;
    
    @Test
    public void shouldOnlyHaveOneAnswer()
    {
        Answer a = answers.save(new Answer( "one" ));
        Question q = questions.save(new Question( "how many answers to this question",  a));

        assertThat( q.getAnswer(), is( a ) );

        Answer a2 = answers.save(new Answer( "1" ));
        q.setAnswer( a2 );
        assertThat( q.getAnswer(), is( a2 ) );
    }
    
    @Test
    public void shouldFindQuestionWithMatchingText()
    {
        Answer a = answers.save(new Answer( "bar" ));
        final String qText = "I say foo, you say";
		Question q = questions.save(new Question( qText,  a));
    	
        Question q2 = questions.findByPropertyValue("text", qText);
        assertThat(q2, is(q));
    }

}
