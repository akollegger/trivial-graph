package org.neo4j.app.trivialt;

//import cucumber.annotation.en.Then;
//import cucumber.annotation.en.When;
//import cucumber.junit.Cucumber;
//import cucumber.junit.Feature;
import cuke4duke.annotation.I18n.EN.When;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.Pending;

import org.junit.runner.RunWith;
import org.neo4j.app.trivialt.App;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.containsString;

//@RunWith(Cucumber.class)
//@Feature(value = "app.feature")
public class AppTest
{
    private Throwable lastProblem;

    @When("^graph-app is run$")
    @Pending
    public void runGraphApp()
    {
        runGraphAppWith( "" );
    }

    @When("^graph-app is run with \"([^\"]*)\"$")
    @Pending
    public void runGraphAppWith( String args )
    {
        String[] splitargs = args.split( " " );
        try
        {
            App.main( splitargs );
        } catch ( Throwable t )
        {
            lastProblem = t;
        }
    }

    @Then("^it should succeed$")
    @Pending
    public void it_succeeds()
    {
        if (lastProblem != null) {
            lastProblem.printStackTrace(  );
        }
    }

    @Then("^it should complain that \"([^\"]*)\"$")
    @Pending
    public void itShouldComplainThat( String complaint )
    {
        assertThat( lastProblem, is( not( nullValue() ) ) );
        assertThat( lastProblem.getMessage(), containsString( complaint ) );
    }

}


