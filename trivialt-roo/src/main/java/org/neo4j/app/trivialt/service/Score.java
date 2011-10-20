package org.neo4j.app.trivialt.service;

public class Score {
	
	Long id;
	String name;
	Integer score;
	
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
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public void accumulate(Integer score) {
		this.score += score;
	}
	
	
}
