// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.service;

import java.lang.String;

privileged aspect Trivia_Roo_JavaBean {
    
    public String Trivia.getCategory() {
        return this.category;
    }
    
    public void Trivia.setCategory(String category) {
        this.category = category;
    }
    
    public String Trivia.getQuestion() {
        return this.question;
    }
    
    public void Trivia.setQuestion(String question) {
        this.question = question;
    }
    
    public String Trivia.getAnswer() {
        return this.answer;
    }
    
    public void Trivia.setAnswer(String answer) {
        this.answer = answer;
    }
    
}