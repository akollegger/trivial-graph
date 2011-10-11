package org.neo4j.app.trivialt.graph.model;

import org.neo4j.app.trivialt.graph.util.NodeMap;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Collection of trivia answers.
 */
public class Answers
{
    private GraphDatabaseService graphdb;
    private NodeMap answerMap;

    public Answers( GraphDatabaseService graphdb )
    {
        this.graphdb = graphdb;
        answerMap = NodeMap.named( "answers", graphdb);
    }

    public Collection<Answer> getAll()
    {
        Collection<Answer> allAnswers = new ArrayList<Answer>();
        for (Node qNode : answerMap.values())
        {
            allAnswers.add( new Answer( qNode ) );
        }
        return allAnswers;
    }

    public Answer remember( String answerText )
    {
        Node node = answerMap.get( answerText );
        if (node == null) {
            node = answerMap.put( answerText, graphdb.createNode() );
        }
        Answer answer = new Answer( node );
        answer.setText( answerText );
        return answer;
    }
}
