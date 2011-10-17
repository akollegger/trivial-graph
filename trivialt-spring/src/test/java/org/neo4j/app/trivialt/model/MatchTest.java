package org.neo4j.app.trivialt.model;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.app.trivialt.Neo4jTestBase;
import org.neo4j.app.trivialt.repository.MatchRepository;
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
public class MatchTest extends Neo4jTestBase
{
    @Autowired private PlayerRepository players;
    @Autowired private MatchRepository matches;
    
    @Test
    public void shouldStoreTeams()
    {
    	Player player = players.save(new Player());
    	Match saved = matches.save(new Match(player, "zimzam"));
    	Match found = matches.findByPropertyValue("title", "zimzam");

        assertThat(found, is(saved));
    }

    @Test
    public void shouldHaveATriviaMaster()
    {
        Player player = players.save(new Player( "@akollegger", "Andreas Kollegger" ));
    	Match saved = matches.save(new Match(player, "zimzam"));
    	
        assertThat( saved.getTriviaMaster(), is( player ) );

    }

}
