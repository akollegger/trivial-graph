package org.neo4j.app.trivialt.model;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.app.trivialt.Neo4jTestBase;
import org.neo4j.app.trivialt.repository.AnswerRepository;
import org.neo4j.cineasts.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Unit tests for Answers.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/trivialt-test-context.xml"})
@Transactional
public class AnswerTest extends Neo4jTestBase
{

    @Autowired private AnswerRepository answers;
    
    @Test
    @Ignore
    public void shouldBeSameAnswerForSameText()
    {
        Answer a1 = answers.save(new Answer("one"));
        Answer a2 = answers.save(new Answer("one"));

        assertThat( a2, is( a1 ) );
    }
    
    @Test
    public void shouldFindSavedAnswer()
    {
    	Answer saved = answers.save(new Answer("save me"));
    	Answer found = answers.findByPropertyValue("text", "save me");
    	
    	assertThat(found, is (saved));
    }

}
