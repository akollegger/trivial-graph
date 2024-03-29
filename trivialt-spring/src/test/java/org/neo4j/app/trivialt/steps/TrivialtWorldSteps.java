package org.neo4j.app.trivialt.steps;

import cuke4duke.Table;
import cuke4duke.annotation.After;
import cuke4duke.annotation.Before;
import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.When;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.Pending;
import cuke4duke.spring.StepDefinitions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.neo4j.app.trivialt.model.Category;
import org.neo4j.app.trivialt.model.FreeformTrivia;
import org.neo4j.app.trivialt.model.Question;
import org.neo4j.app.trivialt.service.TrivialtWorld;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

@StepDefinitions
public class TrivialtWorldSteps
{
	@Autowired
    private TrivialtWorld trivialtWorld;

    private Iterable<Category> categories;
    private Question lastQuestion;

    @Given("^these freeform questions:$")
    public void theseFreeformQuestionsWithTable( Table table ) throws Exception
    {
        try
        {
            for ( List<String> row : table.rows() )
            {
                trivialtWorld.learn( new FreeformTrivia( row.get( 2 ), row.get( 0 ), row.get( 1 ) ) );
            }
        } catch ( Exception e )
        {
            e.printStackTrace();
            throw e;
        }
    }

    @When("^looking up all categories$")
    public void lookingUpAllCategories()
    {
        this.categories = trivialtWorld.getAllCategories();
    }

    @Then("^the categories should include \"([^\"]*)\"$")
    public void theCategoriesShouldInclude( String expectedCategories )
    {
        assertThat( this.categories, is( not( nullValue() ) ) );

        Collection<String> knownCatNames = new ArrayList<String>();
        for ( Category cat : categories )
        {
            knownCatNames.add( cat.getName() );
        }
        assertThat( knownCatNames, hasItems( expectedCategories.split( ", " ) ) );
    }

    @When("^asked for a question from the \"([^\"]*)\" category$")
    public void askedForAQuestionFromTheSportCategory( String category )
    {
        Iterable<Question> questionsInCategory = trivialtWorld.getQuestionsInCategory( category );
        assertThat (questionsInCategory, is(not(nullValue())));
		this.lastQuestion = questionsInCategory.iterator().next();
    }

    @Then("^the question posed should be \"([^\"]*)\"$")
    public void theQuestionPosedShouldBe( String expectedQuestion )
    {
        assertThat( lastQuestion.getText(), is( expectedQuestion ) );
    }

    @When("^asked the question \"([^\"]*)\"$")
    public void askedTheQuestion( String askedQuestion )
    {
        this.lastQuestion = trivialtWorld.findQuestion( askedQuestion );
    }

    @Then("^the answer is \"([^\"]*)\"$")
    public void theAnswerToTheAskedQuestionShouldBe( String expectedAnswer )
    {
        assertThat( this.lastQuestion.getAnswer().getText(), is( expectedAnswer ) );
    }

    @Given("^these facts:$")
    @Pending
    public void theseFactsWithTable( Table table )
    {
    }


}


