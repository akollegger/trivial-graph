package org.neo4j.app.trivialt.graph.model;

import cuke4duke.annotation.After;
import cuke4duke.annotation.Before;
import cuke4duke.annotation.I18n;
import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.When;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.Pending;
import org.neo4j.graphdb.Transaction;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class TeamPlayerSteps
{
    private static TrivialtWorld trivialtWorld;
    private static Player currentPlayer;

    @Before("@players")
    public void prepareWorld()
    {
        trivialtWorld = new TrivialtWorld( "players.graphdb" );
    }

    @After("@players")
    public void shutdownWorlds()
    {
        trivialtWorld.shutdown();
    }

    @Given("^these players:$")
    public void thesePlayersWithTable( cuke4duke.Table table ) throws Exception
    {
        try
        {
            for ( List<String> row : table.rows() )
            {
                trivialtWorld.register( row.get( 0 ), row.get( 1 ) );
            }
        } catch ( Exception e )
        {
            e.printStackTrace();
            throw e;
        }

    }

    @Given("^these teams:$")
    public void theseTeamsWithTable( cuke4duke.Table table ) throws Exception
    {
        try
        {
            for ( List<String> row : table.rows() )
            {
                trivialtWorld.register( row.get( 0 ), row.get( 1 ) );
                Player founder = trivialtWorld.findPlayer( (String) row.get( 0 ) );
                Team t = trivialtWorld.establish( founder, row.get( 1 ), row.get( 2 ) );
                for ( String playerHandle : row.get( 3 ).split( ", " ) )
                {
                    trivialtWorld.include( trivialtWorld.findPlayer( playerHandle ), t );
                }

            }
        } catch ( Exception e )
        {
            e.printStackTrace();
            throw e;
        }

    }

    @When("^you register \"([^\"]*)\" with handle \"([^\"]*)\"$")
    public void youRegisterUser( String name, String handle )
    {
        currentPlayer = trivialtWorld.register( handle, name );
        assertThat( currentPlayer, is( not( nullValue() ) ) );
    }

    @Then("^trivialt knows \"([^\"]*)\" is \"([^\"]*)\"$")
    public void trivialtKnowsPlayer( String handle, String name )
    {
        Player foundPlayer = trivialtWorld.findPlayer( handle );
        assertThat( foundPlayer, is( not( nullValue() ) ) );
        assertThat( foundPlayer.getName(), is( name ) );
    }

    @Then("^\"([^\"]*)\" should be the current player$")
    public void assertTheCurrentPlayerIs( String handle )
    {
        assertThat( currentPlayer.getHandle(), is( handle ) );
    }

    @Given("^\"([^\"]*)\" is the current player$")
    public void setTheCurrentPlayerIs( String handle )
    {
        currentPlayer = trivialtWorld.findPlayer( handle );
    }

    @When("^the current player friends \"([^\"]*)\"$")
    public void theCurrentPlayerFriendsSomebody( String otherPlayerHandle )
    {
        Player otherPlayer = trivialtWorld.findPlayer( otherPlayerHandle );
        trivialtWorld.makeFriends( currentPlayer, otherPlayer );
    }

    @Then("^\"([^\"]*)\" is known to \"([^\"]*)\"$")
    public void playerIsKnownToOtherPlayer( String playerAHandle, String playerBHandle )
    {
        Player playerA = trivialtWorld.findPlayer( playerAHandle );
        Player playerB = trivialtWorld.findPlayer( playerBHandle );
        assertThat( playerA.knownPlayers(), hasItem( playerB ) );
    }

    @When("^a new team called \"([^\"]*)\" with secret \"([^\"]*)\" is created$")
    @Pending
    public void aNewTeamCalledGraphistasWithSecretNeo4jIsCreated( String arg1, String arg2 )
    {
    }

    @Then("^\"([^\"]*)\" is a member of \"([^\"]*)\"$")
    @Pending
    public void playerIsAMemberOfTeam( String arg1, String arg2 )
    {
    }

    @When("^the current player tries to join \"([^\"]*)\" by whispering \"([^\"]*)\"$")
    @Pending
    public void theCurrentPlayerTriesToJoinTeam( String arg1, String arg2 )
    {
    }

}


