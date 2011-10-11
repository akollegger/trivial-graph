package org.neo4j.app.trivialt.graph.model;

import org.neo4j.app.trivialt.graph.util.NodeMap;
import org.neo4j.collections.radixtree.RadixTree;
import org.neo4j.collections.radixtree.RadixTreeImpl;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.helpers.collection.MapUtil;
import org.neo4j.kernel.EmbeddedGraphDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * TrivialtWorld: a world of related trivia.
 *
 * Patterns:
 *
 * (category)-[:CATEGORIZES]->(questions)
 *
 * (question)-[:ANSWERED_BY]->(answer)
 * (question)-[:MISDIRECTED_BY]->(answers)
 *
 * (player)-[:KNOWS]->(players)
 *
 * (team)<-[:MEMBER]-(player)
 * (team)-[:OWNS]->(cards)
 *
 * (card)-[:CONTAINS]->(proposals)
 * (card)->[:SUBMITTED_TO]->(round)
 *
 * (round)-[:PRESENTS.order]->(frame)
 *
 * (frame)-[:PHRASES]->(question)
 * (frame)-[:PROPOSES]->(answer)
 *
 * (proposal)-[:IN_RESPONSE_TO]->(frame)
 * (proposal)-[:PROPOSES]->(answer)
 *
 * (match)-[:ROUND.order]->(round)
 *
 * (media)-[:RENDERS]->(frame)
 * (media-library)-[:STORES]->(media)
 */
public class TrivialtWorld
{
    public static final String NON_TRIVIAL_GRAPH_TYPE = "NON TRIVIAL GRAPH";
    public static final String TRIVIALT_GRAPH_TYPE = "org.neo4j.app.trivialt";

    private GraphDatabaseService graphdb;
    private Categories categories;
    private Questions questions;
    private Answers answers;

    public TrivialtWorld( String pathToDatabase )
    {
        this( new File( pathToDatabase ) );
    }

    public TrivialtWorld( File graphdir )
    {
        this( new EmbeddedGraphDatabase( graphdir.getAbsolutePath() ) );
    }

    public TrivialtWorld( GraphDatabaseService graphdb )
    {
        this.graphdb = graphdb;
        // check whether this is a TrivialtWorld graph
        if ( !confirmGraphType( graphdb ) )
            throw new RuntimeException( graphdb.toString() + " is not a trivialt graph database" );

        registerShutdownHook();

        categories = new Categories(graphdb);
        questions = new Questions(graphdb);
        answers = new Answers(graphdb);
    }

    private boolean confirmGraphType( GraphDatabaseService graphdb )
    {
        Node refNode = graphdb.getReferenceNode();
        if ( refNode == null )
        {
            return false;
        }
        Iterator<Node> nodeIt = graphdb.getAllNodes().iterator();
        nodeIt.next();
        if ( nodeIt.hasNext() ) // graph has multiple nodes, so it isn't fresh
        {
            String graphType = (String) refNode.getProperty( "graph-type", NON_TRIVIAL_GRAPH_TYPE );
            if ( (graphType.equals( NON_TRIVIAL_GRAPH_TYPE )) || (!graphType.equals( TRIVIALT_GRAPH_TYPE )) )
            {
                return false;
            }
        }
        else
        {
            Transaction tx = graphdb.beginTx();
            try
            {
                refNode.setProperty( "graph-type", TRIVIALT_GRAPH_TYPE );
                tx.success();
            } finally
            {
                tx.finish();
            }
        }
        return true;
    }

    private void registerShutdownHook()
    {
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                shutdown();
            }
        } );
    }

    public void shutdown()
    {
        if ( graphdb != null )
        {
            graphdb.shutdown();
        }
    }

    public void learn( FreeformTrivia freeformTrivia )
    {
        Answer answer = answers.remember( freeformTrivia.getAnswer() );
        Question question = questions.remember( freeformTrivia.getQuestion(), answer );
        categories.categorize(freeformTrivia, question);
    }

    public Collection<Category> getAllCategories()
    {
        return categories.getAll();
    }

    public String answer( String question )
    {
        Question q = findQuestion( question );
        return q.getAnswer().getText();
    }

    public Question findQuestion( String question )
    {
        return questions.find(question);
    }

    public Collection<Question> getQuestionsInCategory(String category)
    {
        return categories.find(category).getQuestions();
    }
}
