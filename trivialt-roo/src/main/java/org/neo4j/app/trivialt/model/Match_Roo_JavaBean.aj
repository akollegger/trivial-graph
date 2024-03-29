// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import java.lang.Boolean;
import java.lang.String;
import java.util.Set;
import org.neo4j.app.trivialt.model.Deck;
import org.neo4j.app.trivialt.model.Player;
import org.neo4j.app.trivialt.model.Round;

privileged aspect Match_Roo_JavaBean {
    
    public String Match.getTitle() {
        return this.title;
    }
    
    public void Match.setTitle(String title) {
        this.title = title;
    }
    
    public String Match.getIntroduction() {
        return this.introduction;
    }
    
    public void Match.setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    
    public Boolean Match.getFeatured() {
        return this.featured;
    }
    
    public void Match.setFeatured(Boolean featured) {
        this.featured = featured;
    }
    
    public Boolean Match.getAvailable() {
        return this.available;
    }
    
    public void Match.setAvailable(Boolean available) {
        this.available = available;
    }
    
    public Set<Round> Match.getRounds() {
        return this.rounds;
    }
    
    public void Match.setRounds(Set<Round> rounds) {
        this.rounds = rounds;
    }
    
    public Set<Deck> Match.getDecks() {
        return this.decks;
    }
    
    public void Match.setDecks(Set<Deck> decks) {
        this.decks = decks;
    }
    
    public Player Match.getTriviaMaster() {
        return this.triviaMaster;
    }
    
    public void Match.setTriviaMaster(Player triviaMaster) {
        this.triviaMaster = triviaMaster;
    }
    
    public Round Match.getCurrentRound() {
        return this.currentRound;
    }
    
    public void Match.setCurrentRound(Round currentRound) {
        this.currentRound = currentRound;
    }
    
}
