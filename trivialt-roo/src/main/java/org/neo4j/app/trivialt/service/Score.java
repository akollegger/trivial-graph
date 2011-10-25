package org.neo4j.app.trivialt.service;

public class Score {
	
	Long id;
	String name;
	Integer matchScore=0;
	Integer roundScore=0;
	
  public Score() {;}

	public Score(long id, String name, int roundScore, int matchScore) {
		this.id = id;
		this.name = name;
		this.roundScore = roundScore;
		this.matchScore = matchScore;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void accumulateMatch(Integer score) {
		this.matchScore += score;
	}
	public void accumulateRound(Integer score) {
		this.roundScore += score;
	}

	public Integer getMatchScore() {
		return matchScore;
	}

	public void setMatchScore(Integer matchScore) {
		this.matchScore = matchScore;
	}

	public Integer getRoundScore() {
		return roundScore;
	}

	public void setRoundScore(Integer roundScore) {
		this.roundScore = roundScore;
	}
	
	
}
