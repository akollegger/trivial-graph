package org.neo4j.app.trivialt.graph.model;

import org.neo4j.app.trivialt.graph.util.NodeMap;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Collection of trivia questions.
 */
public class Questions
{
    private static final RelationshipType ANSWER_REL = DynamicRelationshipType.withName( "answer" );

    private GraphDatabaseService graphdb;
    private NodeMap questionMap;

    public Questions( GraphDatabaseService graphdb )
    {
        this.graphdb = graphdb;
        questionMap = NodeMap.named( "questions", graphdb );
    }

    public Collection<Question> getAll()
    {
        Collection<Question> allQs = new ArrayList<Question>();
        for ( Node qNode : questionMap.values() )
        {
            allQs.add( new Question( qNode ) );
        }
        return allQs;
    }

    public Question remember( String questionText, Answer answer )
    {
        Node qNode = questionMap.get( questionText );
        if ( qNode == null )
        {
            qNode = questionMap.put( questionText, graphdb.createNode() );
        }
        Question rememberedQuestion = new Question(qNode);
        rememberedQuestion.setText( questionText );
        rememberedQuestion.setAnswer(answer);
        return rememberedQuestion;
    }

    public Question find( String questionText )
    {
        Question foundQuestion = null;
        Node node = questionMap.get( questionText );
        if (node != null) {
            foundQuestion = new Question(node);
        }
        return foundQuestion;
    }
}
