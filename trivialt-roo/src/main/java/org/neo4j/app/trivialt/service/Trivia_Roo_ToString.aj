// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.service;

import java.lang.String;

privileged aspect Trivia_Roo_ToString {
    
    public String Trivia.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Answer: ").append(getAnswer()).append(", ");
        sb.append("Category: ").append(getCategory()).append(", ");
        sb.append("Question: ").append(getQuestion());
        return sb.toString();
    }
    
}