// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import java.lang.String;
import org.neo4j.app.trivialt.model.Answer;
import org.neo4j.app.trivialt.model.Category;

privileged aspect Question_Roo_JavaBean {
    
    public String Question.getText() {
        return this.text;
    }
    
    public void Question.setText(String text) {
        this.text = text;
    }
    
    public Answer Question.getAnswer() {
        return this.answer;
    }
    
    public void Question.setAnswer(Answer answer) {
        this.answer = answer;
    }
    
    public Category Question.getCategory() {
        return this.category;
    }
    
    public void Question.setCategory(Category category) {
        this.category = category;
    }
    
}
