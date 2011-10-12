package org.neo4j.app.trivialt.graph;

import cuke4duke.Table;
import cuke4duke.annotation.I18n.EN.Given;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.I18n.EN.When;
import cuke4duke.annotation.Pending;
import org.neo4j.app.trivialt.graph.model.Category;
import org.neo4j.app.trivialt.graph.model.FreeformTrivia;
import org.neo4j.app.trivialt.graph.model.TrivialtWorld;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import java.util.Collection;
import java.util.List;

public class SanitySteps
{

    @Given("^checking sanity of TrivialtWorld")
    public void theseFreeformQuestionsWithTable( ) throws Exception
    {
        TrivialtWorld trivialtWorld = new TrivialtWorld(new EmbeddedGraphDatabase("sanity.graphdb"));
        trivialtWorld.learn(new FreeformTrivia("cat", "q", "a"));
        trivialtWorld.shutdown();
    }

}


