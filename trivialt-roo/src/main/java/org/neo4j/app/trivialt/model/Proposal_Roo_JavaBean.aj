// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import java.lang.Integer;
import java.lang.String;
import org.neo4j.app.trivialt.model.Card;
import org.neo4j.app.trivialt.model.FramedQuestion;

privileged aspect Proposal_Roo_JavaBean {
    
    public Integer Proposal.getScore() {
        return this.score;
    }
    
    public void Proposal.setScore(Integer score) {
        this.score = score;
    }
    
    public String Proposal.getProposedAnswer() {
        return this.proposedAnswer;
    }
    
    public void Proposal.setProposedAnswer(String proposedAnswer) {
        this.proposedAnswer = proposedAnswer;
    }
    
    public Card Proposal.getCard() {
        return this.card;
    }
    
    public void Proposal.setCard(Card card) {
        this.card = card;
    }
    
    public FramedQuestion Proposal.getFramedQuestion() {
        return this.framedQuestion;
    }
    
    public void Proposal.setFramedQuestion(FramedQuestion framedQuestion) {
        this.framedQuestion = framedQuestion;
    }
    
}
