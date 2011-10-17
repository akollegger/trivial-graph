package org.neo4j.app.trivialt.model;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.app.trivialt.Neo4jTestBase;
import org.neo4j.app.trivialt.repository.PlayerRepository;
import org.neo4j.app.trivialt.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;

/**
 * Unit tests for Teams.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/trivialt-test-context.xml"})
@Transactional
public class TeamTest extends Neo4jTestBase
{

    @Autowired private TeamRepository teams;
    @Autowired private PlayerRepository players;
    
    @Test
    public void shouldStoreTeams()
    {
    	Team saved = teams.save(new Team("zteam", "zimzam"));
    	Team found = teams.findByPropertyValue("name", "zteam");

        assertThat(found, is(saved));
    }

    @Test
    public void shouldAddPlayersToMembership()
    {
        Player abk = players.save(new Player( "@akollegger", "Andreas Kollegger" ));
        Player mh = players.save(new Player( "@micha", "Michael Hunger" ));
    	Team t = teams.save(new Team("zteam", "zimzam"));
    	
    	t.add(abk);
    	t.add(mh);

        assertThat( t.getMembers(), hasItem( abk ) );
        assertThat( t.getMembers(), hasItem( mh ) );

    }

}
