// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import java.lang.Boolean;
import java.lang.String;
import java.util.Set;
import org.neo4j.app.trivialt.model.Answer;
import org.neo4j.app.trivialt.model.Question;

privileged aspect FramedQuestion_Roo_JavaBean {
    
    public Boolean FramedQuestion.getAvailable() {
        return this.available;
    }
    
    public void FramedQuestion.setAvailable(Boolean available) {
        this.available = available;
    }
    
    public String FramedQuestion.getPhrase() {
        return this.phrase;
    }
    
    public void FramedQuestion.setPhrase(String phrase) {
        this.phrase = phrase;
    }
    
    public Question FramedQuestion.getOriginalQuestion() {
        return this.originalQuestion;
    }
    
    public void FramedQuestion.setOriginalQuestion(Question originalQuestion) {
        this.originalQuestion = originalQuestion;
    }
    
    public Set<Answer> FramedQuestion.getPossibleAnswers() {
        return this.possibleAnswers;
    }
    
    public void FramedQuestion.setPossibleAnswers(Set<Answer> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }
    
}
