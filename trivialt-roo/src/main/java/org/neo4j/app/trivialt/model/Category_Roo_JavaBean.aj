// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import java.lang.String;
import java.util.Set;
import org.neo4j.app.trivialt.model.Question;

privileged aspect Category_Roo_JavaBean {
    
    public String Category.getName() {
        return this.name;
    }
    
    public void Category.setName(String name) {
        this.name = name;
    }
    
    public Set<Question> Category.getQuestions() {
        return this.questions;
    }
    
    public void Category.setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
    
}
