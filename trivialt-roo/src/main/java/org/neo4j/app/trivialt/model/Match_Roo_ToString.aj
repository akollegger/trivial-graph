// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import java.lang.String;

privileged aspect Match_Roo_ToString {
    
    public String Match.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Available: ").append(getAvailable()).append(", ");
        sb.append("CurrentRound: ").append(getCurrentRound()).append(", ");
        sb.append("Decks: ").append(getDecks() == null ? "null" : getDecks().size()).append(", ");
        sb.append("Featured: ").append(getFeatured()).append(", ");
        sb.append("Introduction: ").append(getIntroduction()).append(", ");
        sb.append("Rounds: ").append(getRounds() == null ? "null" : getRounds().size()).append(", ");
        sb.append("Title: ").append(getTitle()).append(", ");
        sb.append("TriviaMaster: ").append(getTriviaMaster());
        return sb.toString();
    }
    
}
