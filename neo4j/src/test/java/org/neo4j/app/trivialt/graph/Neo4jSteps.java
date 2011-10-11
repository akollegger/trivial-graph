package org.neo4j.app.trivialt.graph;

//import cucumber.annotation.en.Given;
//import cucumber.annotation.en.Then;
//import cucumber.annotation.en.When;
//import cucumber.junit.Cucumber;
//import cucumber.junit.Feature;
import cuke4duke.annotation.I18n.EN.Then;
import cuke4duke.annotation.I18n.EN.Given;
import org.junit.runner.RunWith;
import org.neo4j.app.trivialt.graph.util.SelfCleaningGraphDatabase;
import org.neo4j.graphdb.Transaction;

import java.io.File;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Neo4jSteps {
    @Then("^\"([^\"]*)\" contains Neo4j graph files$")
    public void directoryHasNeo4jFiles(String pathToCheck) {
        File dirToCheck = new File(pathToCheck);
        assertTrue(dirToCheck.exists());
        File neostore = new File( dirToCheck, "neostore" );
        assertTrue(neostore.exists());
    }

    @Given("^a small neo4j database in directory \"([^\"]*)\" with at least (\\d+) nodes$$")
    public void createAGenericNeo4jDatabaseInDirectory_(String pathToDatabase, int nodecount) {
        SelfCleaningGraphDatabase genericGraphDatabase = new SelfCleaningGraphDatabase( pathToDatabase );
        Transaction tx = genericGraphDatabase.beginTx();
        try
        {
            for (int i=0; i<nodecount; i++)
                genericGraphDatabase.createNode();
            tx.success();
        }
        finally
        {
            tx.finish();
        }
        genericGraphDatabase.shutdown();
    }


}


