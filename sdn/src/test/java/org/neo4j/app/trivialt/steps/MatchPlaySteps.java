package org.neo4j.app.trivialt.steps;

import cuke4duke.annotation.After;
import cuke4duke.annotation.Before;
import cuke4duke.annotation.I18n;
import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.When;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.Pending;
import cuke4duke.spring.StepDefinitions;

import org.neo4j.app.trivialt.model.Deck;
import org.neo4j.app.trivialt.model.Match;
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
import static org.junit.Assert.assertTrue;

@StepDefinitions
public class MatchPlaySteps {
	
	@Autowired
    private TrivialtWorld trivialt;
	
    @Given("^these matches:$")
    public void theseMatches(cuke4duke.Table table) throws Exception {
        try
        {
            for ( List<String> row : table.rows() )
            {
            	Player triviaMaster = trivialt.findPlayer(row.get(0));
            	assertThat(triviaMaster, is(not(nullValue())));
            	trivialt.createMatch(triviaMaster, row.get(1));
            }
        } catch ( Exception e )
        {
            e.printStackTrace();
            throw e;
        }
    }

	@When("^the current player creates a match called \"([^\"]*)\"$")
	public void theCurrentPlayerCreateAMatchCalled(String matchTitle) {
		assertThat(Current.player, is(not(nullValue())));
		Current.match = trivialt.createMatch(Current.player, matchTitle);
		assertThat(Current.match, is (not(nullValue())));
	}

	@When("^the current player creates a duplicate match called \"([^\"]*)\"$")
	public void theCurrentPlayerCreateADuplicateMatchCalled(String matchTitle) {
		assertThat(Current.player, is(not(nullValue())));
		assertThat(trivialt.createMatch(Current.player, matchTitle), is (nullValue()));
	}
	
	@Given("^\"([^\"]*)\" is the current match")
	public void isTheCurrentMatch(String matchTitle)
	{
		Current.match = trivialt.findMatch(matchTitle);
		assertThat(Current.match, is(not(nullValue())));
	}
	
	@Then("^\"([^\"]*)\" should be the current match")
	public void shouldBeTheCurrentMatch(String matchTitle)
	{
		assertThat(Current.match.getTitle(), is(matchTitle));
		Match foundMatch = trivialt.findMatch(matchTitle);
		assertThat(foundMatch, is(Current.match));
	}

	@Then("^the current match is in \"([^\"]*)\" mode$")
	public void theCurrentMatchIsInAMode(String mode) {
		assertThat(Current.match.getMode(), is(mode));
	}

	@Then("^the current match has (\\d*) rounds?$")
	public void theMatchAbstractFactsHas1Round(Integer count) {
		assertThat(Current.match.getRounds().size(), is(count));
	}

	@Then("^\"([^\"]*)\" is the Trivia Master of \"([^\"]*)\"$")
	public void playerIsTheTriviaMasterOfMatch(String playerHandle, String matchTitle) {
		Player expectedTriviaMaster = trivialt.findPlayer(playerHandle);
		assertThat(expectedTriviaMaster, is(not(nullValue())));
		Match matchToCheck = trivialt.findMatch(matchTitle);
		assertThat(matchToCheck, is(not(nullValue())));
		
		assertThat(matchToCheck.getTriviaMaster(), is(expectedTriviaMaster));
	}

	@Given("^the current player is the Trivia Master of \"([^\"]*)\"$")
	public void theCurrentPlayerIsTheTriviaMasterOfMatch(String matchTitle) {
		Player expectedTriviaMaster = Current.player;
		assertThat(expectedTriviaMaster, is(not(nullValue())));
		Match matchToCheck = trivialt.findMatch(matchTitle);
		assertThat(matchToCheck, is(not(nullValue())));
		
		assertThat(matchToCheck.getTriviaMaster(), is(expectedTriviaMaster));
	}
	
	@Then("^\"([^\"]*)\" is not the Trivia Master of \"([^\"]*)\"$")
	public void playerIsNotTheTriviaMasterOfMatch(String playerHandle, String matchTitle) {
		Player expectedNonTriviaMaster = trivialt.findPlayer(playerHandle);
		assertThat(expectedNonTriviaMaster, is(not(nullValue())));
		Match matchToCheck = trivialt.findMatch(matchTitle);
		assertThat(matchToCheck, is(not(nullValue())));
		
		assertThat(matchToCheck.getTriviaMaster(), is(not(expectedNonTriviaMaster)));
	}

	@When("^the current player adds a round to the match$")
	public void theCurrentPlayerAddsARoundToTheMatch() {
		trivialt.addRound(Current.player, Current.match, "untitled");
	}

	@When("^the current player opens the current match for registration$")
	public void theCurrentPlayerOpensAbstractFactsForRegistration() {
		Match matchToOpen = Current.match;
		matchToOpen.setMode(Match.REGISTRATION);
	}

	@Given("^\"([^\"]*)\" is in \"([^\"]*)\" mode$")
	public void setMatchToMode(String matchTitle, String mode) {
		Match matchToChange = trivialt.findMatch(matchTitle);
		matchToChange.setMode(mode);
	}

	@When("^the current team joins the match \"([^\"]*)\"$")
	public void theCurrentTeamJoinsTheMatch(String matchTitle) {
		Match matchToJoin = trivialt.findMatch(matchTitle);
		assertThat("Specified match does not exist", matchToJoin, is(not(nullValue())));
		Deck teamDeck = trivialt.join(Current.team, matchToJoin);
		assertThat("Team was not issued a deck when joining match", teamDeck, is(not(nullValue())));
		Current.match = teamDeck.getMatch();
		assertThat("New Deck issued for Team is not associated with a Match", Current.match, is(not(nullValue())));
		
	}
	
	@Then("^\"([^\"]*)\" has a deck for \"([^\"]*)\"$")
	public void teamHasADeckForMatch(String teamName, String matchTitle) {
		Team namedTeam = trivialt.findTeam(teamName);
		assertThat(namedTeam, is(not(nullValue())));
		Deck foundDeck = null;
		for (Deck deck : namedTeam.getDecks())
		{
			if (deck.getMatch().getTitle().equals(matchTitle)) {
				foundDeck = deck;
				break;
			}
		}
		assertThat("No Deck found for match", foundDeck, is(not(nullValue())));
	}


}
