package org.neo4j.app.trivialt.graph.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.app.trivialt.model.Player;
import org.neo4j.app.trivialt.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/trivialt-test-context.xml"})
@Transactional
public class ModelTest {
    @Autowired
    protected PlayerRepository playerRepository;

    @Test
    public void fail() {
        Player created = new Player("systay", "Andrés Taylor").persist();
        Player fetched = playerRepository.findByPropertyValue("handle", "systay");

        assertThat(created, is(fetched));

    }

}
