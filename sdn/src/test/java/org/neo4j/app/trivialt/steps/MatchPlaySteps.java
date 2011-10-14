package org.neo4j.app.trivialt.steps;

import cuke4duke.annotation.After;
import cuke4duke.annotation.Before;
import cuke4duke.annotation.I18n;
import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.When;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.Pending;
import cuke4duke.spring.StepDefinitions;

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

@StepDefinitions
public class MatchPlaySteps {
	
	@Autowired
    private TrivialtWorld trivialtWorld;
	
    @Given("^these matches:$")
    public void theseMatches(cuke4duke.Table table) throws Exception {
        try
        {
            for ( List<String> row : table.rows() )
            {
            	Player triviaMaster = trivialtWorld.findPlayer(row.get(0));
            	assertThat(triviaMaster, is(not(nullValue())));
            	trivialtWorld.createMatch(triviaMaster, row.get(1));
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
		Current.match = trivialtWorld.createMatch(Current.player, matchTitle);
		assertThat(Current.match, is (not(nullValue())));
	}

	@When("^the current player creates a duplicate match called \"([^\"]*)\"$")
	public void theCurrentPlayerCreateADuplicateMatchCalled(String matchTitle) {
		assertThat(Current.player, is(not(nullValue())));
		assertThat(trivialtWorld.createMatch(Current.player, matchTitle), is (nullValue()));
	}
	
	@Then("^\"([^\"]*)\" is the current match")
	public void isTheCurrentMatch(String matchTitle)
	{
		Current.match = trivialtWorld.findMatch(matchTitle);
		assertThat(Current.match, is(not(nullValue())));
	}
	
	@Then("^\"([^\"]*)\" should be the current match")
	public void shouldBeTheCurrentMatch(String matchTitle)
	{
		assertThat(Current.match.getTitle(), is(matchTitle));
		Match foundMatch = trivialtWorld.findMatch(matchTitle);
		assertThat(foundMatch, is(Current.match));
	}

	@Then("^the current match is in \"([^\"]*)\" mode$")
	public void theCurrentMatchIsInAMode(String mode) {
		assertThat(Current.match.getMode(), is(Match.UNPREPARED));
	}

	@Then("^the current match has (\\d*) rounds?$")
	public void theMatchAbstractFactsHas1Round(Integer count) {
		assertThat(Current.match.getRounds().size(), is(count));
	}

	@Then("^\"([^\"]*)\" is the Trivia Master of \"([^\"]*)\"$")
	public void playerIsTheTriviaMasterOfMatch(String playerHandle, String matchTitle) {
		Player expectedTriviaMaster = trivialtWorld.findPlayer(playerHandle);
		assertThat(expectedTriviaMaster, is(not(nullValue())));
		Match matchToCheck = trivialtWorld.findMatch(matchTitle);
		assertThat(matchToCheck, is(not(nullValue())));
		
		assertThat(matchToCheck.getTriviaMaster(), is(expectedTriviaMaster));
	}
	
	@Then("^\"([^\"]*)\" is not the Trivia Master of \"([^\"]*)\"$")
	public void playerIsNotTheTriviaMasterOfMatch(String playerHandle, String matchTitle) {
		Player expectedNonTriviaMaster = trivialtWorld.findPlayer(playerHandle);
		assertThat(expectedNonTriviaMaster, is(not(nullValue())));
		Match matchToCheck = trivialtWorld.findMatch(matchTitle);
		assertThat(matchToCheck, is(not(nullValue())));
		
		assertThat(matchToCheck.getTriviaMaster(), is(not(expectedNonTriviaMaster)));
	}

	@When("^the current player adds a round to the match$")
	public void theCurrentPlayerAddsARoundToTheMatch() {
		trivialtWorld.addRound(Current.player, Current.match, "untitled");
	}

	@When("^the current player opens \"([^\"]*)\" for registration$")
	@Pending
	public void theCurrentPlayerOpensAbstractFactsForRegistration(String arg1) {
	}

	@Given("^\"([^\"]*)\" is in \"([^\"]*)\" mode$")
	@Pending
	public void abstractFactsIsInRegistrationMode(String arg1, String arg2) {
	}

	@When("^the current player enters \"([^\"]*)\" to the match \"([^\"]*)\"$")
	@Pending
	public void theCurrentPlayerEntersGermanDivToTheMatchAbstractFacts_(String arg1, String arg2) {
	}

	@Then("^\"([^\"]*)\" receives a playing card for \"([^\"]*)\"$")
	@Pending
	public void germanDivReceivesAPlayingCardForAbstractFacts_(String arg1, String arg2) {
	}

}
