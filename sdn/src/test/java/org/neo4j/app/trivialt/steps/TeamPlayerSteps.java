package org.neo4j.app.trivialt.steps;

import cuke4duke.annotation.After;
import cuke4duke.annotation.Before;
import cuke4duke.annotation.I18n;
import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.When;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.Pending;
import cuke4duke.spring.StepDefinitions;

import org.neo4j.app.trivialt.model.Player;
import org.neo4j.app.trivialt.model.Team;
import org.neo4j.app.trivialt.service.TrivialtWorld;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.internal.matchers.IsCollectionContaining.hasItem;
import static org.junit.matchers.JUnitMatchers.hasItems;

@StepDefinitions
public class TeamPlayerSteps
{
	@Autowired
    private TrivialtWorld trivialtWorld;
    private static Player currentPlayer;
    

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
                    trivialtWorld.draft( trivialtWorld.findPlayer( playerHandle ), t );
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
        assertThat( playerA.getFriends(), hasItem( playerB ) );
    }

    @When("^the current player creates a new team called \"([^\"]*)\" with secret \"([^\"]*)\"$")
    public void aNewTeamCalledGraphistasWithSecretNeo4jIsCreated( String teamName, String secret )
    {
    	trivialtWorld.establish(currentPlayer, teamName, secret);
    }

    @Then("^\"([^\"]*)\" is a member of \"([^\"]*)\"$")
    public void playerIsAMemberOfTeam( String playerHandle, String teamName )
    {
    	Player player = trivialtWorld.findPlayer(playerHandle);
    	Team team = trivialtWorld.findTeam(teamName);
    	assertThat(player.getMemberships(), hasItem(team));
    	assertThat(team.getMembers(), hasItem(player));
    }

    @Then("^\"([^\"]*)\" is not a member of \"([^\"]*)\"$")
    public void playerIsNotAMemberOfTeam(String playerHandle, String teamName) {
    	Player player = trivialtWorld.findPlayer(playerHandle);
    	Team team = trivialtWorld.findTeam(teamName);
    	assertThat(player.getMemberships(), not(hasItem(team)));
    	assertThat(team.getMembers(), not(hasItem(player)));
    }

    @When("^the current player tries to join \"([^\"]*)\" by whispering \"([^\"]*)\"$")
    public void theCurrentPlayerTriesToJoinTeam( String teamName, String teamSecret )
    {
    	Team team = trivialtWorld.findTeam(teamName);
    	trivialtWorld.considerMembership(currentPlayer, team, teamSecret);
    }

}

