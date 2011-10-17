package org.neo4j.app.trivialt.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.app.trivialt.Neo4jTestBase;
import org.neo4j.app.trivialt.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;

/**
 * Unit tests for Players.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/trivialt-test-context.xml"})
@Transactional
public class PlayerTest extends Neo4jTestBase
{

    @Autowired private PlayerRepository players;
    
    @Test
    public void shouldStorePlayers()
    {
        Player saved = players.save( new Player("@akollegger", "Andreas Kollegger") );
        Player found = players.findByPropertyValue("handle", "@akollegger");

        assertThat(found, is(saved));
    }

    @Test
    public void shouldKnowOtherPlayers()
    {
        Player abk = players.save(new Player( "@akollegger", "Andreas Kollegger" ));
        Player mh = players.save(new Player( "@micha", "Michael Hunger" ));
        abk.addFriend( mh );

        assertThat( abk.getFriends(), hasItem( mh ) );
        assertThat( mh.getFriends(), hasItem( abk ) );

    }

}
