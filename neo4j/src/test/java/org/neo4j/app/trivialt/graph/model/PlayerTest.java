package org.neo4j.app.trivialt.graph.model;

import org.junit.Test;
import org.neo4j.app.trivialt.graph.Neo4jTestBase;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;

/**
 * Unit tests for Players.
 */
public class PlayerTest extends Neo4jTestBase
{
    @Test
    public void shouldKnowOtherPlayers()
    {
        Players players = new Players(graphdb);
        Player abk = players.register("akollegger", "Andreas Kollegger");
        Player mh = players.register("micha", "Michael Hunger");
        abk.knows(mh);

        assertThat( abk.knownPlayers(), hasItem(mh) );
        assertThat( mh.knownPlayers(), hasItem(abk) );

    }
}
