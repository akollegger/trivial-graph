// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.util.Set;
import org.neo4j.app.trivialt.model.FramedQuestion;

privileged aspect Round_Roo_JavaBean {
    
    public String Round.getTitle() {
        return this.title;
    }
    
    public void Round.setTitle(String title) {
        this.title = title;
    }
    
    public Boolean Round.getClosed() {
        return this.closed;
    }
    
    public void Round.setClosed(Boolean closed) {
        this.closed = closed;
    }
    
    public Integer Round.getPointsPerQuestion() {
        return this.pointsPerQuestion;
    }
    
    public void Round.setPointsPerQuestion(Integer pointsPerQuestion) {
        this.pointsPerQuestion = pointsPerQuestion;
    }
    
    public Set<FramedQuestion> Round.getFramedQuestions() {
        return this.framedQuestions;
    }
    
    public void Round.setFramedQuestions(Set<FramedQuestion> framedQuestions) {
        this.framedQuestions = framedQuestions;
    }
    
}
