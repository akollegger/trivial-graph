package org.neo4j.app.trivialt.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.app.trivialt.Neo4jTestBase;
import org.neo4j.app.trivialt.repository.Categories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;

/**
 * Unit tests for Players.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/trivialt-test-context.xml"})
@Transactional
public class CategoryTest extends Neo4jTestBase
{

    @Autowired private Categories categories;
    
    @Test
    public void shouldStoreCategories()
    {
    	Category saved = categories.save(new Category("music"));
    	
    	assertTrue(categories.findAll().iterator().hasNext());
    	
    	Category found = categories.findByPropertyValue("name", "music");
    	
        assertThat(found, is(saved));
    }

}
