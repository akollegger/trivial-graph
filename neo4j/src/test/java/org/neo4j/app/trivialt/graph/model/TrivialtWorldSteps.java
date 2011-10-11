package org.neo4j.app.trivialt.graph.model;

import cuke4duke.Table;
import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.When;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.Pending;

import java.util.Collection;
import java.util.List;

public class TrivialtWorldSteps
{

    private static TrivialtWorld trivialtWorld;

    @Given("^a trivialt graph database in directory \"([^\"]*)\"$")
    public void aTrivialtGraphDatabaseInDirectory( String pathToDatabase )
    {
        trivialtWorld = new TrivialtWorld( pathToDatabase );
        trivialtWorld.shutdown();
    }

    @Given("^these freeform questions:$")
    public void theseFreeformQuestionsWithTable( Table table )
    {
        for ( List<String> row : table.rows() )
        {
            trivialtWorld.learn( new FreeformTrivia( row.get( 2 ), row.get( 0 ), row.get( 1 ) ) );
        }
    }

    @When("^looking in the database$")
    public void lookingInTheDatabase()
    {
        ; // a no-top placeholder step
    }

    @Then("^the categories should include \"([^\"]*)\"$")
    @Pending
    public void theCategoriesShouldInclude( String expectedCategories )
    {
        Collection<Category> actualCategories = trivialtWorld.getAllCategories();

    }

    @When("^asked for a question from the \"([^\"]*)\" category$")
    @Pending
    public void askedForAQuestionFromTheSportCategory( String arg1 )
    {
    }

    @Then("^the question posed should be \"([^\"]*)\"$")
    @Pending
    public void theQuestionPosedShouldBe( String expectedQuestion )
    {
    }

    @When("^asked the question \"([^\"]*)\"$")
    @Pending
    public void askedTheQuestion( String askedQuestion )
    {
    }

    @Then("^the answer should be \"([^\"]*)\"$")
    @Pending
    public void theAnswerToTheAskedQuestionShouldBe( String expectedAnswer )
    {
    }

    @Given("^these facts:$")
    @Pending
    public void theseFactsWithTable( Table table )
    {
    }


}


