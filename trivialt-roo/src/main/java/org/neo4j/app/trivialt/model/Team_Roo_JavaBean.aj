// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import java.lang.String;
import java.util.Set;
import org.neo4j.app.trivialt.model.Deck;
import org.neo4j.app.trivialt.model.Player;

privileged aspect Team_Roo_JavaBean {
    
    public String Team.getName() {
        return this.name;
    }
    
    public void Team.setName(String name) {
        this.name = name;
    }
    
    public String Team.getSecret() {
        return this.secret;
    }
    
    public void Team.setSecret(String secret) {
        this.secret = secret;
    }
    
    public Set<Deck> Team.getDecks() {
        return this.decks;
    }
    
    public void Team.setDecks(Set<Deck> decks) {
        this.decks = decks;
    }
    
    public Set<Player> Team.getMembers() {
        return this.members;
    }
    
    public void Team.setMembers(Set<Player> members) {
        this.members = members;
    }
    
}
