package org.neo4j.app.trivialt.model;

import org.junit.Before;
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
import static org.junit.matchers.JUnitMatchers.hasItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/trivialt-test-context.xml"})
@Transactional
public class ModelTest {
    @Autowired
    protected PlayerRepository playerRepository;
    private Player andres;
    private Player andreas;

    @Before
    public void init() {
        andres = new Player("systay", "Andrés Taylor").persist();
        andreas = new Player("akoelleger", "Andreas Koelleger");
    }

    @Test
    public void persistAndThenLoadThroughSpring() {
        Player fetched = playerRepository.findByPropertyValue("handle", "systay");

        assertThat(andres, is(fetched));
    }

    @Test
    public void testThatFriendsAreLoaded() throws Exception {
        andres.addFriend(andreas);
        andres.persist();

        Player loadedAndres = playerRepository.findByPropertyValue("handle", "systay");

        assertThat(loadedAndres.getFriends(), hasItem(andreas));
    }


}
