package org.neo4j.app.trivialt.model;

import static org.neo4j.graphdb.Direction.OUTGOING;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

@NodeEntity
public class Match {

    public static final String UNPREPARED = "unprepared";

	@Indexed
    private String title;

    @RelatedToVia(elementClass = Ordinal.class, type = "ROUND", direction = OUTGOING)
    private Iterable<Ordinal> rounds;
    
    private Player triviaMaster;
    
    private String mode;
    
    private int roundCount=0;
    
    public Match() { ; }

    public Match(Player triviaMaster, String title) {
    	setTriviaMaster(triviaMaster);
    	setTitle(title);
    	setMode(UNPREPARED);
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Player getTriviaMaster() {
		return triviaMaster;
	}

	public void setTriviaMaster(Player triviaMaster) {
		this.triviaMaster = triviaMaster;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<Round> getRounds() {
		List<Round> orderedRounds = new ArrayList<Round>();
		for (Ordinal ord : rounds) {
			orderedRounds.add(ord.getPosition(), ord.getRound());
		}
		return orderedRounds;
	}

	public void addRound(String titleOfRound) {
		Round round = new Round(titleOfRound);
		round.persist();
		Ordinal ord = relateTo(round, Ordinal.class, "ROUND");
		ord.setPosition(roundCount++);
	}

}
