// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.neo4j.app.trivialt.model;

import java.lang.String;

privileged aspect Round_Roo_ToString {
    
    public String Round.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Available: ").append(getAvailable()).append(", ");
        sb.append("CurrentQuestion: ").append(getCurrentQuestion()).append(", ");
        sb.append("FramedQuestions: ").append(getFramedQuestions() == null ? "null" : getFramedQuestions().size()).append(", ");
        sb.append("PointsPerQuestion: ").append(getPointsPerQuestion()).append(", ");
        sb.append("Title: ").append(getTitle());
        return sb.toString();
    }
    
}
