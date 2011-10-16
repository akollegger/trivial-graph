package org.neo4j.app.trivialt.model;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.springframework.data.neo4j.annotation.NodeEntity;

/**
 * A Proposal is a Player's proposed Answer to a framed Question.
 */
@NodeEntity
public class Proposal
{
    private Answer proposedAnswer;
    private FramedQuestion framedQuestion;
    private Player player;
    
    public Proposal() { ; }

    public Proposal(FramedQuestion framedQuestion)
    {
    	setFramedQuestion(framedQuestion);
    }

	public Answer getProposedAnswer() {
		return proposedAnswer;
	}

	public FramedQuestion getFramedQuestion() {
		return framedQuestion;
	}

	public void setFramedQuestion(FramedQuestion framedQuestion) {
		this.framedQuestion = framedQuestion;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	
    
}
